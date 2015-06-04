package com.group3.pcremote.api;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public class UDPClient extends AsyncTask<Void, Void, Void> {
	private String mCommand = null;
	private Fragment mContext;

	public UDPClient(Fragment mContext, String mCommand) {
		this.mCommand = mCommand;
		this.mContext = mContext;
	}

	@Override
	protected Void doInBackground(Void... params) {
		DatagramSocket dsk = null;
		try {
			dsk = new DatagramSocket(1234);
		} catch (SocketException e) {
			e.printStackTrace();
		}

		InetAddress addr = null;
		try {
			addr = InetAddress.getByName("192.168.43.230");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		DatagramPacket pk;
		pk = new DatagramPacket(mCommand.getBytes(), mCommand.length(), addr,
				1234);
		try {
			assert dsk != null;
			dsk.send(pk);
		} catch (IOException e) {
			e.printStackTrace();
		}
		dsk.close();

		try {
			dsk = new DatagramSocket(1234);
			byte[] buffer = new byte[128];
			pk = new DatagramPacket(buffer, 128);
			dsk.receive(pk);
			System.out.println("Server: " + pk.getAddress() + ":"
					+ pk.getPort());
			Toast.makeText(mContext.getActivity(),
					new String(buffer, 0, pk.getLength()), Toast.LENGTH_SHORT)
					.show();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (dsk != null)
				dsk.close();
		}
		return null;
	}

}