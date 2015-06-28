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

public class ProcessReceiveMaintainedConnection extends
		AsyncTask<Void, Object, Void> {

	private SenderData mSenderData = null;
	private Fragment mContext = null;
	private DatagramSocket mDatagramSoc = null;

	public ProcessReceiveMaintainedConnection(Fragment mContext,
			DatagramSocket mDatagramSoc) {
		this.mContext = mContext;
		this.mDatagramSoc = mDatagramSoc;
	}

	@Override
	protected Void doInBackground(Void... params) {
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

				if (mSenderData.getCommand().equals(
						SocketConstant.MAINTAIN_CONNECTION)
						&& FragmentControl.mConnectedServerIP.equals(pk
								.getAddress().getHostAddress())) {

					Log.d("SocketMaintainance", "Receive maintained connection");

					publishProgress(SocketConstant.MAINTAIN_CONNECTION);

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

		String command = mSenderData.getCommand();

		if (command.equals(SocketConstant.MAINTAIN_CONNECTION)) {
			Fragment f = mContext.getActivity().getSupportFragmentManager()
					.findFragmentById(R.id.content_frame);
			if (f instanceof FragmentRemoteControl) { // bật cờ duy trì
														// connection
				FragmentControl.mIsMaintainedConnection = true;
			}
		}

	}
}