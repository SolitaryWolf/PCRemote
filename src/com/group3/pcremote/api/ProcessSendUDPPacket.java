package com.group3.pcremote.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.group3.pcremote.FragmentControl;
import com.group3.pcremote.R;
import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.model.SenderData;

public class ProcessSendUDPPacket extends AsyncTask<Void, Void, Void> {
	private SenderData mSenderData = null;
	private Fragment mContext;
	// lý do phải để DatagramSocket vào hàm tạo bởi vì socket khi
	// đc tạo gắn với 1 port cho đến lúc close nên nếu tạo instance mới
	// nhiều lần sẽ bị lỗi Socket EADDRINUSE (Address already in use)
	private DatagramSocket mDatagramSoc = null;

	public ProcessSendUDPPacket(Fragment mContext, SenderData mSenderData,
			DatagramSocket mDatagramSocket) {
		this.mSenderData = mSenderData;
		this.mContext = mContext;
		this.mDatagramSoc = mDatagramSocket;
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (!isCancelled()) {
			try {
				//Log.d("Socket", "ProcessSendUDPPacket is called");			

				mDatagramSoc.setBroadcast(true);
				
				final ByteArrayOutputStream baos = new ByteArrayOutputStream(
						6400);
				final ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(mSenderData);
				final byte[] data = baos.toByteArray();

				DatagramPacket packet = new DatagramPacket(data, data.length,
						getBroadcastAddress(), SocketConstant.PORT);
				while (!isCancelled()) {
					publishProgress();
					mDatagramSoc.send(packet);
					Thread.sleep(4000);
				}
			} catch (IOException e) {
				Log.e("Socket", e.getMessage());
			} catch (InterruptedException e) {
				Log.e("Socket", e.getMessage());
			}
		}
		return null;
	}
	
	
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		Fragment f = mContext.getActivity().getSupportFragmentManager().findFragmentById(R.id.content_frame); //lấy fragment hiện tại
		if (f instanceof FragmentControl) 
			((FragmentControl) f).refreshAvailableDeviceList();
	}

	/*
	 * method get broadcast address
	 */
	InetAddress getBroadcastAddress() throws IOException {
		WifiManager wifi = (WifiManager) mContext.getActivity()
				.getSystemService(Context.WIFI_SERVICE);
		DhcpInfo dhcp = wifi.getDhcpInfo();
		// handle null somehow

		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		return InetAddress.getByAddress(quads);
	}

}