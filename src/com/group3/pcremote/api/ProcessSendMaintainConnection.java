package com.group3.pcremote.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

public class ProcessSendMaintainConnection extends AsyncTask<Void, Void, Void> {
	private SenderData mSenderData = null;
	private Fragment mContext;
	// lý do phải để DatagramSocket vào hàm tạo bởi vì socket khi
	// đc tạo gắn với 1 port cho đến lúc close nên nếu tạo instance mới
	// nhiều lần sẽ bị lỗi Socket EADDRINUSE (Address already in use)
	private DatagramSocket mDatagramSoc = null;
	private String mServerIP = "";

	public ProcessSendMaintainConnection(Fragment mContext, SenderData mSenderData,
			DatagramSocket mDatagramSocket, String mServerIP) {
		this.mSenderData = mSenderData;
		this.mContext = mContext;
		this.mDatagramSoc = mDatagramSocket;
		this.mServerIP = mServerIP;
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (!isCancelled()) {
			try {

				final ByteArrayOutputStream baos = new ByteArrayOutputStream(
						6400);
				final ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(mSenderData);
				final byte[] data = baos.toByteArray();

				// create instance of class InetAddress from serverIP
				InetAddress addr = null;
				addr = InetAddress.getByName(mServerIP);

				DatagramPacket packet = new DatagramPacket(data, data.length,
						addr, SocketConstant.PORT);

				mDatagramSoc.send(packet);
				Log.d("Socket", "Send maintain connection");

			} catch (IOException e) {
				Log.e("Socket", e.getMessage());
			}
		}
		return null;
	}

}