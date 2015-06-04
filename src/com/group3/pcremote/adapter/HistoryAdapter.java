package com.group3.pcremote.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.group3.pcremote.R;
import com.group3.pcremote.model.ServerInfo;

public class HistoryAdapter extends BaseAdapter {
	private Fragment mContext;
	private int mLayoutID;
	private ArrayList<ServerInfo> mALServerInfo;
	private ViewHolder mViewHolder;

	public HistoryAdapter(Fragment mContext, int mLayoutID,
			ArrayList<ServerInfo> mALServerInfo) {
		this.mContext = mContext;
		this.mLayoutID = mLayoutID;
		this.mALServerInfo = mALServerInfo;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater lInflater = mContext.getActivity().getLayoutInflater();
		if (convertView == null) {
			convertView = lInflater.inflate(mLayoutID, null);
			mViewHolder = new ViewHolder();
			mViewHolder.tvServerName = (TextView) convertView
					.findViewById(R.id.tvServerName);
			mViewHolder.tvServerIP = (TextView) convertView
					.findViewById(R.id.tvServerIP);

			convertView.setTag(mViewHolder);
		} else
			mViewHolder = (ViewHolder) convertView.getTag();

		mViewHolder.tvServerName.setText(mALServerInfo.get(position)
				.getServerName());
		mViewHolder.tvServerIP.setText(mALServerInfo.get(position)
				.getServerIP());

		return convertView;
	}
	
	
	@Override
	public int getCount() {
		return mALServerInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return mALServerInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}


	private class ViewHolder {
		TextView tvServerName;
		TextView tvServerIP;
	}
	
}