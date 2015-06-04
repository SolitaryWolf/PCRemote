package com.group3.pcremote.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.model.SenderData;
import com.group3.pcremote.model.ServerInfo;
import com.group3.pcremote.projectinterface.ServerInfoInterface;

public class ProcessReceiveUDPPacket extends AsyncTask<Void, ServerInfo, Void> {

	private SenderData mSenderData = null;
	private Fragment mContext;
	private ServerInfoInterface mServerInfoInterface;

	public ProcessReceiveUDPPacket(Fragment mContext,
			ServerInfoInterface mServerInfoInterface) {
		this.mContext = mContext;
		this.mServerInfoInterface = mServerInfoInterface;
	}

	@Override
	protected Void doInBackground(Void... params) {
		DatagramSocket socket = null;
		try {
			DatagramSocket dsk = new DatagramSocket(SocketConstant.PORT);
			byte[] buffer = new byte[6000];
			DatagramPacket pk = new DatagramPacket(buffer, buffer.length);
			ByteArrayInputStream baos = null;
			ObjectInputStream ois = null;

			while (true) {
				dsk.receive(pk);
				baos = new ByteArrayInputStream(buffer);
				ois = new ObjectInputStream(baos);
				mSenderData = (SenderData) ois.readObject();

				if (mSenderData.getCommand().equals(SocketConstant.SERVER_INFO)) {
					if (mSenderData.getData() instanceof ServerInfo) {
						ServerInfo sInfo = new ServerInfo();
						sInfo.setServerIP(pk.getAddress().getHostName());
						sInfo.setServerName(((ServerInfo) mSenderData.getData())
								.getServerName());
						publishProgress(sInfo);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				socket.disconnect();
				socket.close();
			}

		}
		return null;
	}

	@Override
	protected void onProgressUpdate(ServerInfo... values) {
		mServerInfoInterface.onGetServerInfoDone(values[0]);
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