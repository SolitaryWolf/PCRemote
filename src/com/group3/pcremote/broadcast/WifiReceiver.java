package com.group3.pcremote.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.group3.pcremote.interfaces.WifiInfoInterface;

public class WifiReceiver extends BroadcastReceiver {
	private WifiInfoInterface mWifiInfoInterface;
	
	public WifiReceiver(WifiInfoInterface mWifiInfoInterface)
	{
		this.mWifiInfoInterface = mWifiInfoInterface;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {

		NetworkInfo info = intent
				.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
		if (info != null) {
			if (info.isConnected()) {
				// e.g. To check the Network Name or other info:
				WifiManager wifiManager = (WifiManager) context
						.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				String ssid = wifiInfo.getSSID();
				
				mWifiInfoInterface.onGetWifiInfoDone(ssid); //có kế nối wifi thì trả về tên wifi
			}
			else
				mWifiInfoInterface.onGetWifiInfoDone(""); //khi ko có kết nối wifi thì trả về ""
		}
	}
}
