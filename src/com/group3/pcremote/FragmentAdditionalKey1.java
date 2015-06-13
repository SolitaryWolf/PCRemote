package com.group3.pcremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.group3.pcremote.api.ProcessSendControlCommand;
import com.group3.pcremote.constant.KeyboardConstant;
import com.group3.pcremote.model.KeyboardCommand;
import com.group3.pcremote.model.SenderData;

public class FragmentAdditionalKey1 extends Fragment implements
		View.OnClickListener {
	private Button btnF1;
	private Button btnF2;
	private Button btnF3;
	private Button btnF4;
	private Button btnF5;
	private Button btnF6;
	private Button btnF7;
	private Button btnF8;
	private Button btnF9;
	private Button btnF10;
	private Button btnF11;
	private Button btnF12;
	private Button btnCtrl1;
	private Button btnAlt1;
	private Button btnWin;
	private Button btnShift;
	private Button btnTab;
	private Button btnMore1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_additional_key1,
				container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		btnF1 = (Button) rootView.findViewById(R.id.btnF1);
		btnF2 = (Button) rootView.findViewById(R.id.btnF2);
		btnF3 = (Button) rootView.findViewById(R.id.btnF3);
		btnF4 = (Button) rootView.findViewById(R.id.btnF4);
		btnF5 = (Button) rootView.findViewById(R.id.btnF5);
		btnF6 = (Button) rootView.findViewById(R.id.btnF6);
		btnF7 = (Button) rootView.findViewById(R.id.btnF7);
		btnF8 = (Button) rootView.findViewById(R.id.btnF8);
		btnF9 = (Button) rootView.findViewById(R.id.btnF9);
		btnF10 = (Button) rootView.findViewById(R.id.btnF10);
		btnF11 = (Button) rootView.findViewById(R.id.btnF11);
		btnF12 = (Button) rootView.findViewById(R.id.btnF12);
		btnCtrl1 = (Button) rootView.findViewById(R.id.btnCtrl1);
		btnAlt1 = (Button) rootView.findViewById(R.id.btnAlt1);
		btnWin = (Button) rootView.findViewById(R.id.btnWin);
		btnShift = (Button) rootView.findViewById(R.id.btnShift);
		btnTab = (Button) rootView.findViewById(R.id.btnTab);
		btnMore1 = (Button) rootView.findViewById(R.id.btnMore1);
	}

	private void addEventToFormWidget(View rootView) {
		btnF1.setOnClickListener(this);
		btnF2.setOnClickListener(this);
		btnF3.setOnClickListener(this);
		btnF4.setOnClickListener(this);
		btnF5.setOnClickListener(this);
		btnF6.setOnClickListener(this);
		btnF7.setOnClickListener(this);
		btnF8.setOnClickListener(this);
		btnF9.setOnClickListener(this);
		btnF10.setOnClickListener(this);
		btnF11.setOnClickListener(this);
		btnF12.setOnClickListener(this);
		btnCtrl1.setOnClickListener(this);
		btnAlt1.setOnClickListener(this);
		btnWin.setOnClickListener(this);
		btnShift.setOnClickListener(this);
		btnTab.setOnClickListener(this);
		btnMore1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		int keyCode = -1;

		switch (id) {
		case R.id.btnF1:
			keyCode = KeyboardConstant.VK_F1;
			break;
		case R.id.btnF2:
			keyCode = KeyboardConstant.VK_F2;
			break;
		case R.id.btnF3:
			keyCode = KeyboardConstant.VK_F3;
			break;
		case R.id.btnF4:
			keyCode = KeyboardConstant.VK_F4;
			break;
		case R.id.btnF5:
			keyCode = KeyboardConstant.VK_F5;
			break;
		case R.id.btnF6:
			keyCode = KeyboardConstant.VK_F6;
			break;
		case R.id.btnF7:
			keyCode = KeyboardConstant.VK_F7;
			break;
		case R.id.btnF8:
			keyCode = KeyboardConstant.VK_F8;
			break;
		case R.id.btnF9:
			keyCode = KeyboardConstant.VK_F9;
			break;
		case R.id.btnF10:
			keyCode = KeyboardConstant.VK_F10;
			break;
		case R.id.btnF11:
			keyCode = KeyboardConstant.VK_F11;
			break;
		case R.id.btnF12:
			keyCode = KeyboardConstant.VK_F12;
			break;
		case R.id.btnCtrl1:
			keyCode = KeyboardConstant.VK_CONTROL;
			break;
		case R.id.btnAlt1:
			keyCode = KeyboardConstant.VK_ALT;
			break;
		case R.id.btnWin:
			keyCode = KeyboardConstant.VK_WINDOWS;
			break;
		case R.id.btnShift:
			keyCode = KeyboardConstant.VK_SHIFT;
			break;
		case R.id.btnTab:
			keyCode = KeyboardConstant.VK_TAB;
			break;
		case R.id.btnMore1:
			Fragment fragment = null;
			fragment = new FragmentAdditionalKey2();
			FragmentManager fragManager = getActivity()
					.getSupportFragmentManager();
			FragmentTransaction fragTransaction = fragManager
					.beginTransaction();
			// để khi ấn back quay về cửa sổ trước đó
			// fragTransaction.addToBackStack(null);
			fragTransaction.replace(R.id.frameLayoutBottomMenu, fragment)
					.commit();
			return;
		default:
			break;
		}

		String command = "";
		command = KeyboardConstant.KEYBOARD_COMMAND;
		KeyboardCommand keyboardCommand = new KeyboardCommand();
		keyboardCommand.setKeyboardCode(keyCode);
		keyboardCommand.setPress(KeyboardConstant.PRESS);

		SenderData senderData = new SenderData();
		senderData.setCommand(command);
		senderData.setData(keyboardCommand);

		new ProcessSendControlCommand(FragmentAdditionalKey1.this, senderData,
				FragmentControl.mDatagramSoc,
				FragmentControl.mConnectedServerIP).execute();

	}

}
