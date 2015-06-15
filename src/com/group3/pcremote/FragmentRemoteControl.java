package com.group3.pcremote;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.group3.pcremote.api.ProcessSendControlCommand;
import com.group3.pcremote.constant.KeyboardConstant;
import com.group3.pcremote.constant.MouseConstant;
import com.group3.pcremote.model.Coordinates;
import com.group3.pcremote.model.KeyboardCommand;
import com.group3.pcremote.model.KeyboardEditText;
import com.group3.pcremote.model.MouseClick;
import com.group3.pcremote.model.SenderData;
import com.group3.pcremote.utils.KeyboardUtils;

public class FragmentRemoteControl extends Fragment {
	MainActivity mainActivity;

	private Button btnLeftMouse;
	private Button btnRightMouse;
	private Button btnMiddleMouse;
	private Button btnShowVirtualKeyboard;
	private Button btnShowAdditionalKeyboard;
	private Button btnShowSystemControl;
	private KeyboardEditText txtKeyPress;
	private RelativeLayout relativeLayoutTouchpad;

	private ProcessSendControlCommand mProcessSendControlCommand = null;

	private String command = "";

	private static float x = 0, y = 0;

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
		btnShowAdditionalKeyboard = (Button) rootView
				.findViewById(R.id.btnShowAdditionalKeyboard);
		btnShowSystemControl = (Button) rootView
				.findViewById(R.id.btnShowSystemControl);

		txtKeyPress = (KeyboardEditText) rootView
				.findViewById(R.id.txtKeyPress);
		txtKeyPress.setBackgroundColor(Color.TRANSPARENT);
		relativeLayoutTouchpad = (RelativeLayout) rootView
				.findViewById(R.id.relativeLayoutTouchpad);
	}

	private void addEventToFormWidget(View rootView) {

		btnLeftMouse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				command = MouseConstant.MOUSE_CLICK_COMMAND;

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
				command = MouseConstant.MOUSE_CLICK_COMMAND;

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
				command = MouseConstant.MOUSE_CLICK_COMMAND;

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
				Fragment f = getActivity().getSupportFragmentManager()
						.findFragmentById(R.id.frameLayoutBottomMenu);
				if (f instanceof FragmentAdditionalKey1
						|| f instanceof FragmentAdditionalKey2
						|| f instanceof FragmentSystemControl) {
					getActivity().getSupportFragmentManager()
							.beginTransaction().remove(f).commit();
				}
				
				txtKeyPress.requestFocus();
				openVirtualKeyboard();
			}
		});

		btnShowAdditionalKeyboard
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Fragment f = getActivity().getSupportFragmentManager()
								.findFragmentById(R.id.frameLayoutBottomMenu);
						if (f instanceof FragmentAdditionalKey1
								|| f instanceof FragmentAdditionalKey2) {
							getActivity().getSupportFragmentManager()
									.beginTransaction().remove(f).commit();
							return;
						}
						Fragment fragment = null;
						fragment = new FragmentAdditionalKey1();
						FragmentManager fragManager = getActivity()
								.getSupportFragmentManager();
						FragmentTransaction fragTransaction = fragManager
								.beginTransaction();
						// để khi ấn back quay về cửa sổ trước đó
						// fragTransaction.addToBackStack(null);
						fragTransaction.replace(R.id.frameLayoutBottomMenu,
								fragment).commit();

					}
				});

		btnShowSystemControl.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment f = getActivity().getSupportFragmentManager()
						.findFragmentById(R.id.frameLayoutBottomMenu);
				if (

				f instanceof FragmentSystemControl) {
					getActivity().getSupportFragmentManager()
							.beginTransaction().remove(f).commit();
					return;
				}
				Fragment fragment = null;
				fragment = new FragmentSystemControl();
				FragmentManager fragManager = getActivity()
						.getSupportFragmentManager();
				FragmentTransaction fragTransaction = fragManager
						.beginTransaction();
				// để khi ấn back quay về cửa sổ trước đó
				// fragTransaction.addToBackStack(null);
				fragTransaction.replace(R.id.frameLayoutBottomMenu, fragment)
						.commit();

			}
		});

		TextWatcher inputTextWatcher = new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() < 1)
					return;
				command = KeyboardConstant.KEYBOARD_COMMAND;
				KeyboardCommand keyboardCommand = new KeyboardCommand();
				keyboardCommand.setKeyboardCode(KeyboardUtils.toKeycode(s
						.charAt(s.length() - 1)));
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

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}
		};
		txtKeyPress.addTextChangedListener(inputTextWatcher);
		txtKeyPress.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_DEL) {
						if (keyCode == KeyEvent.KEYCODE_DEL)
							keyCode = KeyboardConstant.VK_BACK_SPACE;
						/*
						 * if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) keyCode =
						 * KeyboardConstant.VK_SHIFT;
						 */

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
						return true;
					}
				}
				return false;
			}
		});

		relativeLayoutTouchpad.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					x = event.getX();
					y = event.getY();
				}

				/*
				 * if (event.getAction() == MotionEvent.ACTION_UP) { x =
				 * event.getX(); y = event.getY(); }
				 */

				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					command = MouseConstant.MOUSE_MOVE_COMMAND;
					Coordinates coo = new Coordinates();
					coo.setX((int) (event.getX() - x));
					coo.setY((int) (event.getY() - y));

					SenderData senderData = new SenderData();
					senderData.setCommand(command);
					senderData.setData(coo);

					mProcessSendControlCommand = new ProcessSendControlCommand(
							FragmentRemoteControl.this, senderData,
							FragmentControl.mDatagramSoc,
							FragmentControl.mConnectedServerIP);
					mProcessSendControlCommand.execute();

					x = event.getX();
					y = event.getY();
				}

				return true;

			}
		});
	}

	// For open keyboard
	public void openVirtualKeyboard() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}

	// For close keyboard
	public void closeVirtualKeyboard(Context mContext) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}
}
