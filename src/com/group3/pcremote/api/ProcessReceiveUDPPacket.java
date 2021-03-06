package com.group3.pcremote.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.group3.pcremote.FragmentControl;
import com.group3.pcremote.FragmentRemoteControl;
import com.group3.pcremote.R;
import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.interfaces.ConnectionOKInterface;
import com.group3.pcremote.interfaces.ServerInfoInterface;
import com.group3.pcremote.model.SenderData;
import com.group3.pcremote.model.ServerInfo;

public class ProcessReceiveUDPPacket extends AsyncTask<Void, Object, Void> {
	private Fragment mContext = null;
	private ServerInfoInterface mServerInfoInterface = null;
	private ConnectionOKInterface mConnectionOKInterface = null;
	private DatagramSocket mDatagramSoc = null;

	public ProcessReceiveUDPPacket(Fragment mContext,
			ServerInfoInterface mServerInfoInterface,
			ConnectionOKInterface mConnectionOkInterface,
			DatagramSocket mDatagramSoc) {
		this.mContext = mContext;
		this.mServerInfoInterface = mServerInfoInterface;
		this.mConnectionOKInterface = mConnectionOkInterface;
		this.mDatagramSoc = mDatagramSoc;
	}

	@Override
	protected Void doInBackground(Void... params) {
		SenderData mSenderData = null;
		while (!isCancelled()) {
			try {
				if (mDatagramSoc == null) {
					Log.d("Socket", "DatagramSocket is null");
					return null;
				}
				byte[] buffer = new byte[6400];
				DatagramPacket pk = new DatagramPacket(buffer, buffer.length);
				ByteArrayInputStream baos = null;
				ObjectInputStream ois = null;

				mDatagramSoc.receive(pk);
				baos = new ByteArrayInputStream(buffer);
				ois = new ObjectInputStream(baos);

				Object receiverData = ois.readObject();

				if (receiverData != null && receiverData instanceof SenderData)
					mSenderData = (SenderData) receiverData;
				else
					continue;
				Log.d("Socket", mSenderData.getCommand());
				// Log.d("Socket", pk.getAddress().getHostAddress());

				if (mSenderData.getCommand().equals(
						SocketConstant.RESPONSE_SERVER_INFO)) {

					/*
					 * Log.d("Socket", ((ServerInfo)
					 * mSenderData.getData()).getServerIP());
					 */

					if (mSenderData.getData() instanceof ServerInfo) {
						ServerInfo sInfo = new ServerInfo();
						sInfo.setServerIP(pk.getAddress().getHostAddress());
						sInfo.setServerName(((ServerInfo) mSenderData.getData())
								.getServerName());
						mSenderData.setData(sInfo);
						publishProgress(mSenderData);
					}
				}

				else if (mSenderData.getCommand().equals(
						SocketConstant.CONNECT_ACCEPT)
						&& !FragmentControl.mIsTimeOut) {
					if (FragmentControl.mConnectedServerIP.equals(pk
							.getAddress().getHostAddress())) {
						FragmentControl.mIsConnected = true;
						ServerInfo sInfo = new ServerInfo();
						sInfo.setServerIP(pk.getAddress().getHostAddress());
						sInfo.setServerName(((ServerInfo) mSenderData.getData())
								.getServerName());
						
						mSenderData.setData(sInfo);
						publishProgress(mSenderData);
					}
				}

				else if (mSenderData.getCommand().equals(
						SocketConstant.CONNECT_REFUSE)
						&& !FragmentControl.mIsTimeOut) {
					if (FragmentControl.mConnectedServerIP.equals(pk
							.getAddress().getHostAddress())) {
						FragmentControl.mIsConnected = false;
						publishProgress(mSenderData);
					}
				}
			} catch (IOException e) {
				Log.d("Socket", e.getMessage());
			} catch (ClassNotFoundException e) {
				Log.d("Socket", e.getMessage());
			} catch (Exception e) {
				Log.d("Socket", e.getMessage());
			}
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		SenderData senderData = null;
		String command = "";
		if (values[0] != null && values[0] instanceof SenderData)
		{
			senderData = (SenderData) values[0];
			if (!senderData.getCommand().equals(""))
				command = senderData.getCommand();
		}
		
		if (command.equals(SocketConstant.RESPONSE_SERVER_INFO)) {
			Log.d("Socket", "Update UI is called");
			if (senderData.getData() != null && senderData.getData() instanceof ServerInfo)
				mServerInfoInterface
						.onGetServerInfoDone((ServerInfo) senderData.getData());
		} else if (command.equals(SocketConstant.CONNECT_ACCEPT)
				|| command.equals(SocketConstant.CONNECT_REFUSE)) {
			Log.d("Socket", "Receive connection signal");
			// lấy fragment hiện tại
			Fragment f = mContext.getActivity().getSupportFragmentManager()
					.findFragmentById(R.id.content_frame);
			if (f instanceof FragmentControl) {
				((FragmentControl) f).dismissProgressBar();
				((FragmentControl) f).cancelRequestTimeoutConnection();
			}

			if (command.equals(SocketConstant.CONNECT_ACCEPT)) {
				Toast.makeText(mContext.getActivity(), "Connected",
						Toast.LENGTH_SHORT).show();
				ServerInfo serverInfo = null;
				if (senderData.getData() != null
						&& senderData.getData() instanceof ServerInfo) {
					serverInfo = (ServerInfo) senderData.getData();
				}
				mConnectionOKInterface.onAcceptConnection(serverInfo);
			} else if (command.equals(SocketConstant.CONNECT_REFUSE))
				Toast.makeText(mContext.getActivity(), "Can't connect",
						Toast.LENGTH_SHORT).show();
		}

	}
}