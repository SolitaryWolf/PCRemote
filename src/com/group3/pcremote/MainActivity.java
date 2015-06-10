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
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.group3.pcremote.adapter.CustomDrawerAdapter;
import com.group3.pcremote.model.DrawerItem;
public class MainActivity extends FragmentActivity {
	// navigation drawer
	private DrawerLayout mDrawerLayout;
	private ListView lvDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

	private List<DrawerItem> mLDrawerItem;
	private CustomDrawerAdapter mDrawerAdapter;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	//điều khiển menu trên action bar
    public static boolean mIsHiddenMenu = false; // mới vô hiện option menu

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		//set background color for navigation drawer
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

	public void selectItem(int possition) {

		Fragment fragment = null;
		switch (possition) {
		case 0:
			fragment = new FragmentControl();
			break;
		case 1:
			fragment = new FragmentSetting();
			break;
		case 2:
			fragment = new FragmentFeedback();
			break;
		case 3:
			fragment = new FragmentHelp();
			break;
		case 4:
			fragment = new FragmentAbout();
			break;

		default:
			break;
		}

		FragmentManager fragManager = getSupportFragmentManager();
		FragmentTransaction fragTransaction = fragManager.beginTransaction();
		// để khi ấn back quay về cửa sổ trước đó
		//fragTransaction.addToBackStack(null); 
		fragTransaction.replace(R.id.content_frame, fragment).commit();

		lvDrawer.setItemChecked(possition, true);
		setTitle(mLDrawerItem.get(possition).getItemName());
		mDrawerLayout.closeDrawer(lvDrawer);

	}

	// =============Navigation drawer=============================//
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

	// ================================================================//

	// =================Option menu========================//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		if (mIsHiddenMenu == true)
	    {
	        for (int i = 0; i < menu.size(); i++)
	            menu.getItem(i).setVisible(false);
	    }
	    else
	    {
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
			Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame); //lấy fragment hiện tại
			if (f instanceof FragmentControl) 
			{
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
	
}
