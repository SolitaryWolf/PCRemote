package com.group3.pcremote.api;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.group3.pcremote.FragmentControl;

public class ProcessCheckMaintainedConnection extends
		AsyncTask<Void, Void, Boolean> {
	private Fragment mContext;

	public ProcessCheckMaintainedConnection(Fragment mContext) {
		this.mContext = mContext;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		while (!isCancelled()) {
			if (FragmentControl.mIsConnected == true
					&& FragmentControl.mCountTime == 5) {
				FragmentControl.mCountTime = 0;
				return FragmentControl.mIsMaintainedConnection;
			}
		}
		
		return true;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result == false)
		{
			mContext.getActivity().getSupportFragmentManager().popBackStack();
		}
			
		super.onPostExecute(result);	
	}

}
