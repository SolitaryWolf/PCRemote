package com.group3.pcremote;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.group3.pcremote.adapter.HistoryAdapter;
import com.group3.pcremote.adapter.ServerInfoAdapter;
import com.group3.pcremote.api.ProcessReceiveUDPPacket;
import com.group3.pcremote.api.ProcessSendUDPPacket;
import com.group3.pcremote.broadcast.WifiReceiver;
import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.model.SenderData;
import com.group3.pcremote.model.ServerInfo;
import com.group3.pcremote.projectinterface.ServerInfoInterface;
import com.group3.pcremote.projectinterface.WifiInfoInterface;

public class FragmentControl extends Fragment implements WifiInfoInterface,
		ServerInfoInterface {

	// wifi change part
	private TextView tvNetWorkConnection;
	private BroadcastReceiver broadcastRecWifiChange = null;

	// list server info
	private ListView lvAvailableDevice;
	private ArrayList<ServerInfo> mALServerInfo;
	private ServerInfoAdapter mServerInfoAdaper;

	// list history
	private ListView lvHistory;
	private ArrayList<ServerInfo> mALHistory;
	private HistoryAdapter mHistoryAdapter;

	// process
	private ProcessSendUDPPacket processSendUDPPacket = null;
	private ProcessReceiveUDPPacket processReceiveUDPacket = null;

	// socket
	private DatagramSocket mDatagramSoc = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_control,
				container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		receiveBroadcastWifiChange();

		// new UDPClient(FragmentControl.this, "Get PC info").execute();
		// new ConnectedPCsInfo().execute();

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		tvNetWorkConnection = (TextView) rootView
				.findViewById(R.id.tvNetworkConnection);

		// list server info
		lvAvailableDevice = (ListView) rootView
				.findViewById(R.id.lvAvailableDevice);
		mALServerInfo = new ArrayList<ServerInfo>();
		mServerInfoAdaper = new ServerInfoAdapter(this,
				R.layout.custom_listview_serverinfo, mALServerInfo);
		lvAvailableDevice.setAdapter(mServerInfoAdaper);

		try {
			mDatagramSoc = new DatagramSocket(SocketConstant.PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	private void addEventToFormWidget(View rootView) {
		lvAvailableDevice
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

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

	// =================================================================//

	@Override
	public void onGetServerInfoDone(ServerInfo serverInfo) {
		if (mALServerInfo.size() > 0) {
			for (ServerInfo sInfo : mALServerInfo) {
				if (sInfo.getServerIP().equals(serverInfo.getServerIP()))
					return;
			}

		}
		mALServerInfo.add(serverInfo);

		mServerInfoAdaper.notifyDataSetChanged();
	}

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
	 * nhận data từ servers
	 */
	public void receiveDataFromServer() {
		processReceiveUDPacket = new ProcessReceiveUDPPacket(
				FragmentControl.this, FragmentControl.this, mDatagramSoc);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			processReceiveUDPacket
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			processReceiveUDPacket.execute();
	}

	public void refreshAvailableDeviceList() {
		// reset list available device
		mALServerInfo.clear();
		mServerInfoAdaper.notifyDataSetChanged();
	}

	private void sendRequestConnect() {
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
}
