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
import com.group3.pcremote.model.ServerInfoHistory;

public class HistoryAdapter extends BaseAdapter {
	private Fragment mContext;
	private int mLayoutID;
	private ArrayList<ServerInfoHistory> mALServerInfoHis;
	private ViewHolder mViewHolder;

	public HistoryAdapter(Fragment mContext, int mLayoutID,
			ArrayList<ServerInfoHistory> mALServerInfoHis) {
		this.mContext = mContext;
		this.mLayoutID = mLayoutID;
		this.mALServerInfoHis = mALServerInfoHis;
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
			mViewHolder.tvLastConnection = (TextView) convertView
					.findViewById(R.id.tvLastConnection);

			convertView.setTag(mViewHolder);
		} else
			mViewHolder = (ViewHolder) convertView.getTag();

		mViewHolder.tvServerName.setText(mALServerInfoHis.get(position)
				.getServerName());
		mViewHolder.tvServerIP.setText(mALServerInfoHis.get(position)
				.getServerIP());
		mViewHolder.tvLastConnection.setText("Last connection: "
				+ mALServerInfoHis.get(position).getConnectedDate());

		return convertView;
	}

	@Override
	public int getCount() {
		return mALServerInfoHis.size();
	}

	@Override
	public Object getItem(int position) {
		return mALServerInfoHis.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder {
		TextView tvServerName;
		TextView tvServerIP;
		TextView tvLastConnection;
	}

}