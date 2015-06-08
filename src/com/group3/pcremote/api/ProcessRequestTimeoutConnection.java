package com.group3.pcremote.api;

import com.group3.pcremote.FragmentControl;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ProgressBar;

public class ProcessRequestTimeoutConnection extends
		AsyncTask<Void, Void, Void> {
	private ProgressDialog mProgressDialog;

	public ProcessRequestTimeoutConnection(ProgressDialog mProgressDialog) {
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
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		mProgressDialog.dismiss();
		FragmentControl.mIsConnected = false;
		
	}

}
