package com.group3.pcremote;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.group3.pcremote.adapter.CustomDrawerAdapter;
import com.group3.pcremote.api.ProcessSendControlCommand;
import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.model.DrawerItem;
import com.group3.pcremote.model.SenderData;

public class MainActivity extends FragmentActivity {
	// navigation drawer
	private DrawerLayout mDrawerLayout;
	private ListView lvDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	private List<DrawerItem> mLDrawerItem;
	private CustomDrawerAdapter mDrawerAdapter;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	// điều khiển menu trên action bar
	public static boolean mIsHiddenMenu = false; // mới vô hiện option menu

	private boolean isPressBackDoubleToDisconnect = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// prevent screen to dim
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01579b")));

		getFormWidgets();
		addEventToFormWidgets();

		if (savedInstanceState == null) {

			selectItem(0);

		}
	}

	private void getFormWidgets() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		lvDrawer = (ListView) findViewById(R.id.left_drawer);
		// set background color for navigation drawer
		int mainColor = Color.parseColor("#01579b");
		lvDrawer.setBackgroundColor(mainColor);

		/*
		 * mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
		 * GravityCompat.START);
		 */

		mLDrawerItem = new ArrayList<DrawerItem>();
		// lấy title của app
		mTitle = mDrawerTitle = getTitle();
		addItemToDrawer();

		mDrawerAdapter = new CustomDrawerAdapter(this,
				R.layout.custom_drawer_item, mLDrawerItem);

		lvDrawer.setAdapter(mDrawerAdapter);

	}

	private void addEventToFormWidgets() {
		lvDrawer.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// creates call to onPrepareOptionsMenu()
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// creates call to onPrepareOptionsMenu()
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	// =============Navigation drawer=============================//
	/*
	 * Add Drawer Item to Drawer item list
	 */
	private void addItemToDrawer() {
		mLDrawerItem.add(new DrawerItem("Control", R.drawable.ic_control));
		mLDrawerItem.add(new DrawerItem("Setting", R.drawable.ic_setting));
		mLDrawerItem.add(new DrawerItem("Feedback", R.drawable.ic_feedback));
		mLDrawerItem.add(new DrawerItem("Help", R.drawable.ic_help));
		mLDrawerItem.add(new DrawerItem("About", R.drawable.ic_about));

	}

	public void selectItem(int position) {
		Fragment f = getSupportFragmentManager().findFragmentById(
				R.id.content_frame);

		Fragment fragment = null;
		switch (position) {
		case 0:
			if (f instanceof FragmentControl) 
			{
				mDrawerLayout.closeDrawer(lvDrawer);
				return;
			}
				
			fragment = new FragmentControl();
			break;
		case 1:
			if (f instanceof FragmentSetting) 
			{
				mDrawerLayout.closeDrawer(lvDrawer);
				return;
			}
			fragment = new FragmentSetting();
			break;
		case 2:
			if (f instanceof FragmentFeedback) 
			{
				mDrawerLayout.closeDrawer(lvDrawer);
				return;
			}
			fragment = new FragmentFeedback();
			break;
		case 3:
			if (f instanceof FragmentHelp) 
			{
				mDrawerLayout.closeDrawer(lvDrawer);
				return;
			}
			fragment = new FragmentHelp();
			break;
		case 4:
			if (f instanceof FragmentAbout) 
			{
				mDrawerLayout.closeDrawer(lvDrawer);
				return;
			}
			fragment = new FragmentAbout();
			break;

		default:
			break;
		}

		FragmentManager fragManager = getSupportFragmentManager();
		FragmentTransaction fragTransaction = fragManager.beginTransaction();
		// để khi ấn back quay về cửa sổ trước đó
		fragTransaction.addToBackStack(null);
		fragTransaction.replace(R.id.content_frame, fragment).commit();

		//lvDrawer.setItemChecked(position, true);
		//setTitle(mLDrawerItem.get(position).getItemName());
		mDrawerLayout.closeDrawer(lvDrawer);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			selectItem(position);

		}
	}
	
	public void changeNavigationDrawerItem(int position)
	{
		lvDrawer.setItemChecked(position, true);
		setTitle(mLDrawerItem.get(position).getItemName());
	}

	// ================================================================//

	// =================Option menu========================//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		if (mIsHiddenMenu == true) {
			for (int i = 0; i < menu.size(); i++)
				menu.getItem(i).setVisible(false);
		} else {
			for (int i = 0; i < menu.size(); i++)
				menu.getItem(i).setVisible(true);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_refresh) {
			Fragment f = getSupportFragmentManager().findFragmentById(
					R.id.content_frame); // lấy fragment hiện tại
			if (f instanceof FragmentControl) {
				((FragmentControl) f).sendBroadcast();

			}
			return true;
		}
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// =====================================================//

	// check internet connection
	public static boolean isNetworkStatusAvialable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
			if (netInfos != null) {
				return netInfos.isConnected();
			}
		}
		return false;
	}

	// xử lý khi press back
	@Override
	public void onBackPressed() {

		int count = getSupportFragmentManager().getBackStackEntryCount();

		if (count == 0)
			super.onBackPressed();

		else { // đang có hơn 1 fragment trong stack

			// lấy fragment hiện tại
			Fragment f = getSupportFragmentManager().findFragmentById(
					R.id.content_frame);

			if (f instanceof FragmentRemoteControl) {

				if (isPressBackDoubleToDisconnect) {
					SenderData senderData = new SenderData();
					senderData
							.setCommand(SocketConstant.NOTIFY_DISCONNECT_FROM_CLIENT);
					senderData.setData(null);
					new ProcessSendControlCommand(f, senderData,
							FragmentControl.mDatagramSoc,
							FragmentControl.mConnectedServerIP).execute();
					super.onBackPressed();
					return;
				}

				isPressBackDoubleToDisconnect = true;
				Toast.makeText(this, "Press back again to disconnect",
						Toast.LENGTH_SHORT).show();

				new Handler().postDelayed(new Runnable() {
					// 2s ko press back again
					@Override
					public void run() {
						isPressBackDoubleToDisconnect = false;
					}
				}, 2000);
				getFragmentManager().popBackStack();
			}
			else if (f instanceof FragmentControl) {
				// do quay về lúc chưa có fragment nào nên tắt app luôn
				finish();
			}
			else
				super.onBackPressed();
		}
	}
}
