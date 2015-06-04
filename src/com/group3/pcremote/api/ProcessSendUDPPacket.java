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

import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.model.SenderData;

public class ProcessSendUDPPacket extends AsyncTask<Void, Void, Void> {	
	private SenderData mSenderData = null;
	private Fragment mContext;

	public ProcessSendUDPPacket(Fragment mContext, SenderData mSenderData) {
		this.mSenderData = mSenderData;
		this.mContext = mContext;
	}

	@Override
	protected Void doInBackground(Void... params) {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(SocketConstant.PORT);
			socket.setBroadcast(true);
			
			final ByteArrayOutputStream baos = new ByteArrayOutputStream(6400);
			final ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(mSenderData);
			final byte[] data = baos.toByteArray();
		
			DatagramPacket packet = new DatagramPacket(data,
					data.length, getBroadcastAddress(), SocketConstant.PORT);
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
			{
				socket.disconnect();
				socket.close();
			}
		}
		return null;
	}

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