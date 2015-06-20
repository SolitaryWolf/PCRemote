package com.group3.pcremote.api;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.group3.pcremote.FragmentControl;

public class ProcessCheckMaintainedConnection extends
		AsyncTask<Void, Boolean, Void> {
	private Fragment mContext;
	private Boolean result;

	public ProcessCheckMaintainedConnection(Fragment mContext) {
		this.mContext = mContext;
	}

	@Override
	protected Void doInBackground(Void... params) {
		while (!isCancelled()) {
			if (FragmentControl.mIsConnected == true) {

				try {
					Thread.sleep(5000);
					Log.d("Socket", "Check maintain connection");
					result = FragmentControl.mIsMaintainedConnection;
					FragmentControl.mIsMaintainedConnection = true;
					publishProgress(result);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}		
			}
		}

		return null;
	}

	@Override
	protected void onProgressUpdate(Boolean... values) {
		if (values[0] == false)
			mContext.getActivity().getSupportFragmentManager().popBackStack();
		super.onProgressUpdate(values);
	}

}
