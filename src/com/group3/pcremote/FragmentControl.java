package com.group3.pcremote;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.group3.pcremote.adapter.HistoryAdapter;
import com.group3.pcremote.adapter.ServerInfoAdapter;
import com.group3.pcremote.api.ProcessReceiveUDPPacket;
import com.group3.pcremote.api.ProcessRequestTimeoutConnection;
import com.group3.pcremote.api.ProcessSendRequestConnect;
import com.group3.pcremote.api.ProcessSendUDPPacket;
import com.group3.pcremote.broadcast.WifiReceiver;
import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.model.ClientInfo;
import com.group3.pcremote.model.SenderData;
import com.group3.pcremote.model.ServerInfo;
import com.group3.pcremote.projectinterface.ConnectionOKInterface;
import com.group3.pcremote.projectinterface.ServerInfoInterface;
import com.group3.pcremote.projectinterface.WifiInfoInterface;
import com.group3.pcremote.utils.NetworkUtils;

public class FragmentControl extends Fragment implements WifiInfoInterface,
		ServerInfoInterface, ConnectionOKInterface {

	// wifi change
	private TextView tvNetWorkConnection;
	private BroadcastReceiver broadcastRecWifiChange = null;

	// list server info
	private ListView lvAvailableDevice;
	private ArrayList<ServerInfo> mALServerInfo;
	private ServerInfoAdapter mServerInfoAdaper;
	private ArrayList<ServerInfo> mALServerInfoTemp;

	// list history
	private ListView lvHistory;
	private ArrayList<ServerInfo> mALHistory;
	private HistoryAdapter mHistoryAdapter;

	// process
	private ProcessSendUDPPacket processSendUDPPacket = null;
	private ProcessReceiveUDPPacket processReceiveUDPacket = null;
	private ProcessSendRequestConnect processSendRequestConnect = null;
	private ProcessRequestTimeoutConnection processRequestTimeoutConnection = null;

	// socket
	public static DatagramSocket mDatagramSoc = null;

	// connection
	public static boolean mIsConnected = false;
	public static String mConnectedServerIP = "";
	public static boolean mIsTimeOut = false;

	// progress dialog
	private ProgressDialog mProgressDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_control,
				container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		tvNetWorkConnection = (TextView) rootView
				.findViewById(R.id.tvNetworkConnection);

		// list server info
		lvAvailableDevice = (ListView) rootView
				.findViewById(R.id.lvAvailableDevice);
		mALServerInfo = new ArrayList<ServerInfo>();
		mALServerInfoTemp = new ArrayList<ServerInfo>();
		mServerInfoAdaper = new ServerInfoAdapter(this,
				R.layout.custom_listview_serverinfo, mALServerInfo);
		lvAvailableDevice.setAdapter(mServerInfoAdaper);

		try {
			mDatagramSoc = new DatagramSocket(SocketConstant.PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		// progress dialog
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setTitle("Connecting...");
		mProgressDialog.setMessage("Please wait");
		mProgressDialog.setCancelable(true);
	}

	private void addEventToFormWidget(View rootView) {
		lvAvailableDevice
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						mIsTimeOut = false;
						processRequestTimeoutConnection = new ProcessRequestTimeoutConnection(
								FragmentControl.this, mProgressDialog);
						processRequestTimeoutConnection.execute();
						mConnectedServerIP = mALServerInfo.get(position)
								.getServerIP().trim();
						sendRequestConnect(mConnectedServerIP);
					}

				});
	}

	// ===========Wifi change=========================================//
	/*
	 * mỗi khi get đc thông tin wifi thì tiến hành update UI
	 */
	@Override
	public void onGetWifiInfoDone(String wifiName) {
		if (wifiName != "")
			tvNetWorkConnection.setText("Wifi: "
					+ wifiName.substring(1, wifiName.length() - 1));
		else
			tvNetWorkConnection.setText("No network found");

		// mỗi khi wifi change thì dừng các thread lại
		if (processSendUDPPacket != null && !processSendUDPPacket.isCancelled())
			processSendUDPPacket.cancel(false); // true thì ngắt ngang, false
												// thì đợi thread hoàn thành
		if (processReceiveUDPacket != null
				&& !processReceiveUDPacket.isCancelled())
			processReceiveUDPacket.cancel(false);

		// reset list available device
		refreshAvailableDeviceList();

		// gửi broadcast cho các máy trong cùng mạng
		// và tiến hành nhận thông tin từ server
		if (wifiName != "") {
			sendBroadcast();
			receiveDataFromServer();
		}
	}

	/*
	 * bắt sự kiện mỗi khi trạng thái wifi thay đổi
	 */
	private void receiveBroadcastWifiChange() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.net.wifi.STATE_CHANGE");
		broadcastRecWifiChange = new WifiReceiver(this);

		getActivity().registerReceiver(broadcastRecWifiChange, intentFilter);
	}

	/*
	 * reset list available device
	 */
	public void refreshAvailableDeviceList() {

		mALServerInfo.clear();
		mServerInfoAdaper.notifyDataSetChanged();
	}

	// =================================================================//

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.group3.pcremote.projectinterface.ServerInfoInterface#onGetServerInfoDone
	 * (com.group3.pcremote.model.ServerInfo) Mỗi khi nhận đc ServerInfo
	 */
	@Override
	public void onGetServerInfoDone(ServerInfo serverInfo) {
		if (mALServerInfoTemp.size() > 0) {
			for (ServerInfo sInfo : mALServerInfoTemp) {
				if (sInfo.getServerIP().equals(serverInfo.getServerIP()))
					return;
			}

		}
		mALServerInfoTemp.add(serverInfo);
	}

	// ==================broadcast=========================================//
	/*
	 * send broadcast đến các máy trong cùng mạng wifi
	 */
	public void sendBroadcast() {
		SenderData senderData = new SenderData();
		senderData.setCommand(SocketConstant.REQUEST_SERVER_INFO);

		processSendUDPPacket = new ProcessSendUDPPacket(FragmentControl.this,
				senderData, mDatagramSoc);
		// ở các phiên bản từ HONEYCOMB trở đi thread sẽ chạy tuần tự nên
		// nếu muốn chạy song song phải dùng AsyncTask.THREAD_POOL_EXECUTOR
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			processSendUDPPacket
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			processSendUDPPacket.execute();
	}

	/*
	 * nhận data từ servers khi gửi broadcast
	 */
	public void receiveDataFromServer() {
		processReceiveUDPacket = new ProcessReceiveUDPPacket(
				FragmentControl.this, FragmentControl.this,
				FragmentControl.this, mDatagramSoc);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			processReceiveUDPacket
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			processReceiveUDPacket.execute();
	}

	// ====================================================================//

	// =================connection========================================//
	/*
	 * send request connection
	 */
	private void sendRequestConnect(String serverIP) {
		SenderData senderData = new SenderData();
		senderData.setCommand(SocketConstant.REQUEST_CONNECT);

		ClientInfo clientInfo = new ClientInfo();
		clientInfo.setClientIP(NetworkUtils.getIPAddress(true));
		clientInfo.setClientName(NetworkUtils.getDeviceName());

		senderData.setData(clientInfo);

		processSendRequestConnect = new ProcessSendRequestConnect(
				FragmentControl.this, senderData, mDatagramSoc, serverIP);
		// ở các phiên bản từ HONEYCOMB trở đi thread sẽ chạy tuần tự nên
		// nếu muốn chạy song song phải dùng AsyncTask.THREAD_POOL_EXECUTOR
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			processSendRequestConnect
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			processSendRequestConnect.execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.group3.pcremote.projectinterface.ConnectionOKInterface#onAcceptConnection
	 * () khi get tín hiệu chấp nhận kết nối
	 */
	@Override
	public void onAcceptConnection() {
		cancelSendBroadcast();
		
		Fragment fragment = new FragmentRemoteControl();

		FragmentManager fragManager = getActivity().getSupportFragmentManager();
		FragmentTransaction fragTransaction = fragManager.beginTransaction();
		// để khi ấn back quay về cửa sổ trước đó
		fragTransaction.addToBackStack(null); 
		fragTransaction.replace(R.id.content_frame, fragment).commit();
	}

	/*
	 * dừng progress bar
	 */
	public void dismissProgressBar() {
		mProgressDialog.dismiss();
	}

	/*
	 * interrupt thread progress time out
	 */
	public void cancelRequestTimeoutConnection() {
		if (processRequestTimeoutConnection != null
				&& !processRequestTimeoutConnection.isCancelled())
			processRequestTimeoutConnection.cancel(true);
	}

	/*
	 * interrupt thread send broadcast
	 */
	public void cancelSendBroadcast() {
		if (processSendUDPPacket != null && !processSendUDPPacket.isCancelled())
			processSendUDPPacket.cancel(true);
	}

	// =======================================================================//

	// cập nhật list available device
	public void updateAvailableDeviceList() {
		boolean isDelected = true;
		boolean isAdded = true;
		if (mALServerInfo.size() == 0)
			mALServerInfo.addAll(mALServerInfoTemp);

		else if (mALServerInfo.size() > 0 && mALServerInfoTemp.size() > 0) {
			// remove những device ko thấy kết nối
			for (int i = 0; i < mALServerInfo.size(); i++) {
				isDelected = true;
				for (int j = 0; j < mALServerInfoTemp.size(); j++) {
					if (mALServerInfo.get(i).getServerIP()
							.equals(mALServerInfoTemp.get(j).getServerIP())) {
						isDelected = false;
						break;
					}
				}
				if (isDelected)
					mALServerInfo.remove(mALServerInfo.get(i));
			}

			// thêm vào những device mới nếu có
			for (int i = 0; i < mALServerInfoTemp.size(); i++) {
				isAdded = true;
				for (int j = 0; j < mALServerInfo.size(); j++) {
					// nếu trùng rồi thì ko thêm vào nữa
					if (mALServerInfoTemp.get(i).getServerIP()
							.equals(mALServerInfo.get(j).getServerIP())) {
						isAdded = false;
						break;
					}
				}
				if (isAdded)
					mALServerInfo.add(mALServerInfoTemp.get(i));
			}
		} else if (mALServerInfoTemp.size() == 0)
			mALServerInfo.clear();

		mServerInfoAdaper.notifyDataSetChanged();
		mALServerInfoTemp.clear();
	}

	/*
	 * interrupt receive data from server
	 */
	public void cancelReceiveDataFromServer() {
		if (processReceiveUDPacket != null
				&& !processReceiveUDPacket.isCancelled())
			processReceiveUDPacket.cancel(true);
	}

	/*
	 * interrupt send request connection
	 */
	public void cancelSendRequestConnect() {
		if (processSendRequestConnect != null
				&& !processSendRequestConnect.isCancelled())
			processSendRequestConnect.cancel(true);
	}

	@Override
	public void onPause() {
		cancelRequestTimeoutConnection();
		cancelSendRequestConnect();
		cancelSendBroadcast();
		cancelReceiveDataFromServer();
		
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();

		// change navigation drawer when press back
		((MainActivity)getActivity()).changeNavigationDrawerItem(0);
		
		mIsConnected = false;
		mConnectedServerIP = "";
		receiveBroadcastWifiChange();
	}

	@Override
	public void onDestroyView() {
		
		// hủy đăng ký
		if (broadcastRecWifiChange != null)
			getActivity().unregisterReceiver(broadcastRecWifiChange);
		super.onDestroyView();
		/*Fragment fragment = (Fragment) getFragmentManager().findFragmentById(
				R.id.content_frame);
		FragmentTransaction fragTransaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		fragTransaction.remove(fragment);
		fragTransaction.commit();*/
			
	}
	
}
