package com.group3.pcremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHelp extends Fragment {
	private TextView tvHelpContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_help,
				container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		tvHelpContent = (TextView) rootView.findViewById(R.id.tvHelpContent);
		tvHelpContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,
			    getResources().getDimension(R.dimen.text_size));
		tvHelpContent
				.setText(Html
						.fromHtml("1. Open app on your Android device.<br/>"
								+ "2. Choose PC in Avaiable Devices then press \"YES\" on PC client. A new frame will be opened, if connection time out, a message will display.<br />"
								+ "3. In the new frame, you can control the PC through touchpad and keyboard. Some special keyboard are supported, some aren't due to Java Client limitation.<br/>"
								+ "4. If you want to send a feedback, please head to \"Feedback\" section in the menu."));
	}

	private void addEventToFormWidget(View rootView) {

	}

	@Override
	public void onResume() {
		super.onResume();

		// change navigation drawer when press back
		((MainActivity) getActivity()).changeNavigationDrawerItem(3);
	}

}
