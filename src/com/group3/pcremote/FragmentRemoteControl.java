package com.group3.pcremote;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.group3.pcremote.api.ProcessSendControlCommand;
import com.group3.pcremote.constant.KeyboardConstant;
import com.group3.pcremote.constant.MouseConstant;
import com.group3.pcremote.model.KeyboardCommand;
import com.group3.pcremote.model.MouseClick;
import com.group3.pcremote.model.SenderData;

public class FragmentRemoteControl extends Fragment {
	private Button btnLeftMouse;
	private Button btnRightMouse;
	private Button btnMiddleMouse;
	private Button btnShowVirtualKeyboard;
	private EditText txtKeyPress;

	private ProcessSendControlCommand mProcessSendControlCommand = null;

	private String command = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_layout_remotecontrol, container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		btnLeftMouse = (Button) rootView.findViewById(R.id.btnLeftMouse);
		btnRightMouse = (Button) rootView.findViewById(R.id.btnRightMouse);
		btnMiddleMouse = (Button) rootView.findViewById(R.id.btnMiddleMouse);
		btnShowVirtualKeyboard = (Button) rootView
				.findViewById(R.id.btnShowVirtualKeyboard);

		txtKeyPress = (EditText) rootView.findViewById(R.id.txtKeyPress);
	}

	private void addEventToFormWidget(View rootView) {

		btnLeftMouse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				command = MouseConstant.MOUSE_COMMAND;

				MouseClick mouseClick = new MouseClick();
				mouseClick.setButtonIndex(MouseConstant.LEFT_MOUSE);
				mouseClick.setPress(MouseConstant.CLICK);

				SenderData senderData = new SenderData();
				senderData.setCommand(command);
				senderData.setData(mouseClick);

				mProcessSendControlCommand = new ProcessSendControlCommand(
						FragmentRemoteControl.this, senderData,
						FragmentControl.mDatagramSoc,
						FragmentControl.mConnectedServerIP);
				mProcessSendControlCommand.execute();

			}
		});

		btnRightMouse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				command = MouseConstant.MOUSE_COMMAND;

				MouseClick mouseClick = new MouseClick();
				mouseClick.setButtonIndex(MouseConstant.RIGHT_MOUSE);
				mouseClick.setPress(MouseConstant.CLICK);

				SenderData senderData = new SenderData();
				senderData.setCommand(command);
				senderData.setData(mouseClick);

				mProcessSendControlCommand = new ProcessSendControlCommand(
						FragmentRemoteControl.this, senderData,
						FragmentControl.mDatagramSoc,
						FragmentControl.mConnectedServerIP);
				mProcessSendControlCommand.execute();

			}
		});

		btnMiddleMouse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				command = MouseConstant.MOUSE_COMMAND;

				MouseClick mouseClick = new MouseClick();
				mouseClick.setButtonIndex(MouseConstant.MIDDLE_MOUSE);
				mouseClick.setPress(MouseConstant.CLICK);

				SenderData senderData = new SenderData();
				senderData.setCommand(command);
				senderData.setData(mouseClick);

				mProcessSendControlCommand = new ProcessSendControlCommand(
						FragmentRemoteControl.this, senderData,
						FragmentControl.mDatagramSoc,
						FragmentControl.mConnectedServerIP);
				mProcessSendControlCommand.execute();

			}
		});

		btnShowVirtualKeyboard.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				txtKeyPress.requestFocus();

			}
		});

		TextWatcher inputTextWatcher = new TextWatcher() {
			public void afterTextChanged(Editable s) {
				command = KeyboardConstant.KEYBOARD_COMMAND;
				KeyboardCommand keyboardCommand = new KeyboardCommand();
				keyboardCommand.setKeyboardCode(charToKeycode(s.charAt(s
						.length() - 1)));
				keyboardCommand.setPress(KeyboardConstant.PRESS);

				SenderData senderData = new SenderData();
				senderData.setCommand(command);
				senderData.setData(keyboardCommand);

				mProcessSendControlCommand = new ProcessSendControlCommand(
						FragmentRemoteControl.this, senderData,
						FragmentControl.mDatagramSoc,
						FragmentControl.mConnectedServerIP);
				mProcessSendControlCommand.execute();

				Log.d("Keyboard", s.charAt(s.length() - 1)
						+ " character to send");
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}
		};
		txtKeyPress.addTextChangedListener(inputTextWatcher);

		/*
		 * txtKeyPress.setOnKeyListener(new View.OnKeyListener() {
		 * 
		 * @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
		 * 
		 * 
		 * Log.d("Keyboard", keyCode + " character(code) to send"); return
		 * false; } });
		 */
	}

	// For open keyboard
	public void openKeyBoard() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	// For close keyboard
	public void closeKeyBoard(Context mContext) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	public int charToKeycode(char c) {
		int keyCode = -1;
		switch (c) {
		/** Key code constant: '0' key. */
		case '0':
			keyCode = 7;
			break;
		/** Key code constant: '1' key. */
		case '1':
			keyCode = 8;
			break;
		/** Key code constant: '2' key. */
		case '2':
			keyCode = 9;
			break;
		/** Key code constant: '3' key. */
		case '3':
			keyCode = 10;
			break;
		/** Key code constant: '4' key. */
		case '4':
			keyCode = 11;
			break;
		/** Key code constant: '5' key. */
		case '5':
			keyCode = 12;
			break;
		/** Key code constant: '6' key. */
		case '6':
			keyCode = 13;
			break;
		/** Key code constant: '7' key. */
		case '7':
			keyCode = 14;
			break;
		/** Key code constant: '8' key. */
		case '8':
			keyCode = 15;
			break;
		/** Key code constant: '9' key. */
		case '9':
			keyCode = 16;
			break;
		}
		return keyCode;
	}

}
