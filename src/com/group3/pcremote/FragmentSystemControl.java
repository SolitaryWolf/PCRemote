package com.group3.pcremote;

import com.group3.pcremote.api.ProcessSendControlCommand;
import com.group3.pcremote.constant.KeyboardConstant;
import com.group3.pcremote.constant.PowerConstant;
import com.group3.pcremote.model.KeyboardCommand;
import com.group3.pcremote.model.PowerCommand;
import com.group3.pcremote.model.SenderData;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentSystemControl extends Fragment {
	private Button btnSleep;
	private Button btnShutdown;
	private Button btnLogoff;
	private Button btnRestart;
	private Button btnHibernate;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_system_control,
				container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		btnSleep = (Button) rootView.findViewById(R.id.btnSleep);
		btnShutdown = (Button) rootView.findViewById(R.id.btnShutdown);
		btnRestart = (Button) rootView.findViewById(R.id.btnRestart);
		btnLogoff = (Button) rootView.findViewById(R.id.btnLogoff);
		btnHibernate = (Button) rootView.findViewById(R.id.btnHibernate);
	}

	private void addEventToFormWidget(View rootView) {
		btnLogoff.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				doSend(PowerConstant.LOG_OFF);
			}
		});

		btnSleep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				doSend(PowerConstant.SLEEP);
			}
		});

		btnShutdown.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				doSend(PowerConstant.SHUTDOWN);
			}
		});

		btnRestart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				doSend(PowerConstant.RESTART);
			}
		});
		
		btnHibernate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				doSend(PowerConstant.HIBERNATE);
			}
		});
	}

	private void doSend(String content) {
		String command = "";
		command = PowerConstant.POWER_COMMAND;
		PowerCommand powerCommand = new PowerCommand(content);
		
		SenderData senderData = new SenderData();
		senderData.setCommand(command);
		senderData.setData(powerCommand);

		new ProcessSendControlCommand(FragmentSystemControl.this, senderData,
				FragmentControl.mDatagramSoc,
				FragmentControl.mConnectedServerIP).execute();
	}

}
