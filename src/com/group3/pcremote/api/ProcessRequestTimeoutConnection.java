package com.group3.pcremote.api;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.group3.pcremote.FragmentControl;

public class ProcessRequestTimeoutConnection extends
		AsyncTask<Void, Void, Void> {
	private Fragment mContext;
	private ProgressDialog mProgressDialog;

	public ProcessRequestTimeoutConnection(Fragment mContext, ProgressDialog mProgressDialog) {
		this.mContext = mContext;
		this.mProgressDialog = mProgressDialog;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog.show();
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			FragmentControl.mIsTimeOut = false;
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Log.e("Socket", "Requesting timeout connection interrupted");
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		mProgressDialog.dismiss();
		FragmentControl.mIsConnected = false;
		FragmentControl.mIsTimeOut = true;
		Toast.makeText(mContext.getActivity(), "Time out", Toast.LENGTH_SHORT).show();
		
	}

}
