package com.group3.pcremote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FragmentFeedback extends Fragment {
	private Button btnSendFeedback;
	private EditText txtFeedbackContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_feedback,
				container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		btnSendFeedback = (Button) rootView.findViewById(R.id.btnSendFeedback);
		txtFeedbackContent = (EditText) rootView
				.findViewById(R.id.txtFeedbackContent);
	}

	private void addEventToFormWidget(View rootView) {
		btnSendFeedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (txtFeedbackContent.getText().toString().trim().length() < 5) {
					Toast.makeText(
							getActivity(),
							"The feedback content must be at least 5 characters",
							Toast.LENGTH_LONG).show();
					return;
				}
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setType("text/email");
				emailIntent.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "hoatongoc@gmail.com" });
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RemotePC Feedback");
				emailIntent.putExtra(Intent.EXTRA_TEXT, txtFeedbackContent
						.getText().toString().trim());
				startActivity(Intent.createChooser(emailIntent,
						"Send Feedback:"));

			}
		});

	}

}
