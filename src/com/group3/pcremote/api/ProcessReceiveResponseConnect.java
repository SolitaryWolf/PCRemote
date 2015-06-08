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
import android.widget.Toast;

import com.group3.pcremote.FragmentControl;
import com.group3.pcremote.R;
import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.model.SenderData;
import com.group3.pcremote.model.ServerInfo;
import com.group3.pcremote.projectinterface.ServerInfoInterface;

public class ProcessReceiveResponseConnect extends AsyncTask<Void, String, Void> {

	private SenderData mSenderData = null;
	private Fragment mContext = null;
	private ServerInfoInterface mServerInfoInterface;
	private DatagramSocket mDatagramSoc = null;
	private String mConnectedServerIP = "";
	
	public ProcessReceiveResponseConnect(Fragment mContext,
			ServerInfoInterface mServerInfoInterface,
			DatagramSocket mDatagramSoc, String mConnectedServerIP) {
		this.mContext = mContext;
		this.mServerInfoInterface = mServerInfoInterface;
		this.mDatagramSoc = mDatagramSoc;
		this.mConnectedServerIP = mConnectedServerIP;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			byte[] buffer = new byte[6400];
			DatagramPacket pk = new DatagramPacket(buffer, buffer.length);
			ByteArrayInputStream baos = null;
			ObjectInputStream ois = null;

			while (!isCancelled()) {
				mDatagramSoc.receive(pk);
				baos = new ByteArrayInputStream(buffer);
				ois = new ObjectInputStream(baos);

				Object receiverData = ois.readObject();
				if (receiverData != null && receiverData instanceof SenderData)
					mSenderData = (SenderData) receiverData;
				Log.d("Socket", mSenderData.getCommand());
				
				if (mSenderData.getCommand().equals(
						SocketConstant.CONNECT_ACCEPT)) {
					if (mConnectedServerIP.equals(pk.getAddress().getHostName()))
					{
						FragmentControl.mIsConnected = true;
						publishProgress(SocketConstant.CONNECT_ACCEPT);
					}
				}
				
				else if (mSenderData.getCommand().equals(
						SocketConstant.CONNECT_REFUSE)) {
					if (mConnectedServerIP.equals(pk.getAddress().getHostName()))
					{
						FragmentControl.mIsConnected = false;
						publishProgress(SocketConstant.CONNECT_REFUSE);
					}
				}
			}

		} catch (IOException e) {
			Log.d("Socket", e.getMessage());
		} catch (ClassNotFoundException e) {
			Log.d("Socket", e.getMessage());
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		Log.d("Socket", "Receive connection signal");
		if (values[0] != "")
		{
			Fragment f = mContext.getActivity().getSupportFragmentManager()
					.findFragmentById(R.id.content_frame); // lấy fragment hiện
															// tại
			if (f instanceof FragmentControl)
				((FragmentControl) f).dismissProgressBar();
			
			if (values[0].equals(SocketConstant.CONNECT_ACCEPT))
				Toast.makeText(mContext.getActivity(), "Connected", Toast.LENGTH_SHORT).show();
			else if (values[0].equals(SocketConstant.CONNECT_REFUSE))
				Toast.makeText(mContext.getActivity(), "Can't connect", Toast.LENGTH_SHORT).show();		
		}
	}
}