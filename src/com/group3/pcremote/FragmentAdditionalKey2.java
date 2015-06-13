package com.group3.pcremote;

import com.group3.pcremote.api.ProcessSendControlCommand;
import com.group3.pcremote.constant.KeyboardConstant;
import com.group3.pcremote.model.KeyboardCommand;
import com.group3.pcremote.model.SenderData;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentAdditionalKey2 extends Fragment implements
		View.OnClickListener {
	private Button btnEsc;
	private Button btnHome;
	private Button btnEnd;
	private Button btnIns;
	private Button btnDel;
	private Button btnPgUp;
	private Button btnEnter;
	private Button btnNumLock;
	private Button btnCaps;
	private Button btnF10;
	private Button btnUp;
	private Button btnPgDown;
	private Button btnMore2;
	private Button btnCtrl2;
	private Button btnAlt2;
	private Button btnLeft;
	private Button btnDown;
	private Button btnRight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_additional_key2,
				container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		btnEsc = (Button) rootView.findViewById(R.id.btnEsc);
		btnHome = (Button) rootView.findViewById(R.id.btnHome);
		btnEnd = (Button) rootView.findViewById(R.id.btnEnd);
		btnIns = (Button) rootView.findViewById(R.id.btnIns);
		btnDel = (Button) rootView.findViewById(R.id.btnDel);
		btnPgUp = (Button) rootView.findViewById(R.id.btnPgUp);
		btnEnter = (Button) rootView.findViewById(R.id.btnEnter);
		btnNumLock = (Button) rootView.findViewById(R.id.btnNumLock);
		btnCaps = (Button) rootView.findViewById(R.id.btnCaps);
		btnUp = (Button) rootView.findViewById(R.id.btnUp);
		btnPgDown = (Button) rootView.findViewById(R.id.btnPgDown);
		btnMore2 = (Button) rootView.findViewById(R.id.btnMore2);
		btnCtrl2 = (Button) rootView.findViewById(R.id.btnCtrl2);
		btnAlt2 = (Button) rootView.findViewById(R.id.btnAlt2);
		btnLeft = (Button) rootView.findViewById(R.id.btnLeft);
		btnDown = (Button) rootView.findViewById(R.id.btnDown);
		btnRight = (Button) rootView.findViewById(R.id.btnRight);
	}

	private void addEventToFormWidget(View rootView) {
		btnEsc.setOnClickListener(this);
		btnHome.setOnClickListener(this);
		btnEnd.setOnClickListener(this);
		btnIns.setOnClickListener(this);
		btnDel.setOnClickListener(this);
		btnPgUp.setOnClickListener(this);
		btnEnter.setOnClickListener(this);
		btnNumLock.setOnClickListener(this);
		btnCaps.setOnClickListener(this);
		btnUp.setOnClickListener(this);
		btnPgDown.setOnClickListener(this);
		btnMore2.setOnClickListener(this);
		btnCtrl2.setOnClickListener(this);
		btnAlt2.setOnClickListener(this);
		btnLeft.setOnClickListener(this);
		btnDown.setOnClickListener(this);
		btnRight.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		int keyCode = -1;

		switch (id) {
		case R.id.btnEsc:
			keyCode = KeyboardConstant.VK_ESCAPE;
			break;
		case R.id.btnHome:
			keyCode = KeyboardConstant.VK_HOME;
			break;
		case R.id.btnEnd:
			keyCode = KeyboardConstant.VK_END;
			break;
		case R.id.btnIns:
			keyCode = KeyboardConstant.VK_INSERT;
			break;
		case R.id.btnDel:
			keyCode = KeyboardConstant.VK_DELETE;
			break;
		case R.id.btnPgUp:
			keyCode = KeyboardConstant.VK_PAGE_UP;
			break;
		case R.id.btnEnter:
			keyCode = KeyboardConstant.VK_ENTER;
			break;
		case R.id.btnNumLock:
			keyCode = KeyboardConstant.VK_NUM_LOCK;
			break;
		case R.id.btnCaps:
			keyCode = KeyboardConstant.VK_CAPS_LOCK;
			break;
		case R.id.btnUp:
			keyCode = KeyboardConstant.VK_UP;
			break;
		case R.id.btnPgDown:
			keyCode = KeyboardConstant.VK_PAGE_DOWN;
			break;
		case R.id.btnCtrl2:
			keyCode = KeyboardConstant.VK_CONTROL;
			break;
		case R.id.btnAlt2:
			keyCode = KeyboardConstant.VK_ALT;
			break;
		case R.id.btnLeft:
			keyCode = KeyboardConstant.VK_LEFT;
			break;
		case R.id.btnDown:
			keyCode = KeyboardConstant.VK_DOWN;
			break;
		case R.id.btnRight:
			keyCode = KeyboardConstant.VK_RIGHT;
			break;
		case R.id.btnMore2:
			Fragment fragment = null;
			fragment = new FragmentAdditionalKey1();
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

		new ProcessSendControlCommand(FragmentAdditionalKey2.this, senderData,
				FragmentControl.mDatagramSoc,
				FragmentControl.mConnectedServerIP).execute();

	}

}
