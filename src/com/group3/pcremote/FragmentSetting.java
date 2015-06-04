package com.group3.pcremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentSetting extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_setting, container, false);
		
		getFormWidgets(rootView);
		addEventToFormWidget(rootView);
		
		return rootView;
	}
	
	private void getFormWidgets(View rootView)
	{
		
	}
	
	private void addEventToFormWidget(View rootView)
	{
		
		
	}


}
