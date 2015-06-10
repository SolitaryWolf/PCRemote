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
		/*btnLeftMouse = (Button) rootView.findViewById(R.id.btnLeftMouse);
		btnRightMouse = (Button) rootView.findViewById(R.id.btnRightMouse);
		btnMiddleMouse = (Button) rootView.findViewById(R.id.btnMiddleMouse);
		btnShowVirtualKeyboard = (Button) rootView
				.findViewById(R.id.btnShowVirtualKeyboard);*/

		txtKeyPress = (EditText) rootView.findViewById(R.id.txtKeyPress);
	}

	private void addEventToFormWidget(View rootView) {

		/*btnLeftMouse.setOnClickListener(new View.OnClickListener() {

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
				openKeyBoard();

			}
		});*/

		TextWatcher inputTextWatcher = new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Log.d("Keyboard", s.charAt(count - 1) + " character to send");
				;
			}
		};
		txtKeyPress.addTextChangedListener(inputTextWatcher);

		txtKeyPress.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				command = KeyboardConstant.KEYBOARD_COMMAND;
				KeyboardCommand keyboardCommand = new KeyboardCommand();
				keyboardCommand.setKeyboardCode(keyCode);
				keyboardCommand.setPress(KeyboardConstant.PRESS);

				SenderData senderData = new SenderData();
				senderData.setCommand(command);
				senderData.setData(keyboardCommand);

				mProcessSendControlCommand = new ProcessSendControlCommand(
						FragmentRemoteControl.this, senderData,
						FragmentControl.mDatagramSoc,
						FragmentControl.mConnectedServerIP);
				mProcessSendControlCommand.execute();

				Log.d("Keyboard", keyCode + " character(code) to send");
				return false;
			}
		});
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

}
