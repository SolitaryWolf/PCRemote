package com.group3.pcremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentAbout extends Fragment {
	private TextView tvAboutContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_about,
				container, false);

		getFormWidgets(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		tvAboutContent = (TextView) rootView.findViewById(R.id.tvAboutContent);
		// make link can be clicked 
		tvAboutContent.setMovementMethod (LinkMovementMethod.getInstance());

		tvAboutContent.setText(Html.fromHtml("<b>" + "Remote Control" + "</b>" + "<br />"
				+ "<i>Copyright © 2015-2016 UIT Group </i>" + "<br />"
				+ "<i>All rights reserved</i>" + "<br />"
				+ "<i>Version 1.0</i>" + "<br/>"
				+ "<small>" + "This app helps you to control your PC remotely and easily" + "</small>" + "<br />" 
				+ "<b>Contact us</b>" + "<br/>"
				+ "<b>Email :</b>solitarywolf.uit@gmail.com" + "<br/>"
				+ "<b>Facebook :</b><a href=\"https://www.facebook.com/solitarywolf.uit\">SolitaryWolf's facebook</a>" + "<br/>"
				+ "<b>Blog :</b><a href=\"https://solitarywolf-it.blogspot.com/\">SolitaryWolf's blog</a>"));

	}
	
	// remove fragment when close fragment
	@Override
	public void onDestroyView() {
		Fragment fragment = (Fragment) getFragmentManager().findFragmentById(
				R.id.content_frame);
		FragmentTransaction fragTransaction = getActivity()
				.getSupportFragmentManager().beginTransaction();
		fragTransaction.remove(fragment);
		fragTransaction.commit();

		super.onDestroyView();
	}
}
