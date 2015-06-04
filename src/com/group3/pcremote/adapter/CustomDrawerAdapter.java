package com.group3.pcremote.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group3.pcremote.R;
import com.group3.pcremote.model.DrawerItem;

public class CustomDrawerAdapter extends ArrayAdapter<DrawerItem> {

	private Context mContext;
	private List<DrawerItem> mLDrawerItem;
	private int mLayoutResID;

	public CustomDrawerAdapter(Context context, int layoutResourceID,
			List<DrawerItem> listItems) {
		super(context, layoutResourceID, listItems);
		this.mContext = context;
		this.mLDrawerItem = listItems;
		this.mLayoutResID = layoutResourceID;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		DrawerItemHolder drawerHolder;
		View view = convertView;

		if (view == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			view = inflater.inflate(mLayoutResID, parent, false);
			drawerHolder.ItemName = (TextView) view
					.findViewById(R.id.drawer_itemName);
			drawerHolder.icon = (ImageView) view.findViewById(R.id.drawer_icon);

			drawerHolder.itemLayout = (LinearLayout) view
					.findViewById(R.id.itemLayout);

			view.setTag(drawerHolder);

		} else {
			drawerHolder = (DrawerItemHolder) view.getTag();

		}

		DrawerItem dItem = (DrawerItem) this.mLDrawerItem.get(position);

		drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
				dItem.getImgResID()));
		drawerHolder.ItemName.setText(dItem.getItemName());

		return view;
	}

	private static class DrawerItemHolder {
		TextView ItemName;
		ImageView icon;
		LinearLayout itemLayout;
	}
}
