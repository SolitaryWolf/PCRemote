package com.group3.pcremote;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

import com.group3.pcremote.api.ProcessCheckMaintainedConnection;
import com.group3.pcremote.api.ProcessReceiveMaintainedConnection;
import com.group3.pcremote.api.ProcessSendControlCommand;
import com.group3.pcremote.api.ProcessSendMaintainedConnection;
import com.group3.pcremote.constant.KeyboardConstant;
import com.group3.pcremote.constant.MouseConstant;
import com.group3.pcremote.constant.SocketConstant;
import com.group3.pcremote.model.Coordinates;
import com.group3.pcremote.model.KeyboardCommand;
import com.group3.pcremote.model.KeyboardEditText;
import com.group3.pcremote.model.MouseClick;
import com.group3.pcremote.model.MouseScroll;
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
	private ProcessCheckMaintainedConnection mProcessCheckMaintainedConnection = null;
	private ProcessSendMaintainedConnection mProcessSendMaintainedConnection = null;
	private ProcessReceiveMaintainedConnection mProcessReceiveMaintainedConnection = null;

	private String command = "";

	// coordinate on touchpad
	private static float x = 0, y = 0;
	private static float yScroll = 0;
	private static final int MAX_CLICK_DURATION = 150;
	private long mClickTime = -1;
	private long mClickTime2 = -1;
	private long mClickTime3 = -1;
	// finger number 1
	private boolean FLAG_ON_POINTER1_DOWN = false;
	// finger number 2
	private boolean FLAG_ON_POINTER2_DOWN = false;

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
		
		if (FragmentControl.sIsMouseButtonsOn == false)
		{
			btnLeftMouse.setVisibility(View.GONE);
			btnRightMouse.setVisibility(View.GONE);
			btnMiddleMouse.setVisibility(View.GONE);
		}
		
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

		// get keycode when u press virtual keyboard
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

		// make button back work
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
				int action = event.getAction() & MotionEvent.ACTION_MASK;

				switch (action) {
				case MotionEvent.ACTION_DOWN:
					if (event.getPointerCount() == 1) {
						mClickTime = System.currentTimeMillis();
						x = event.getX();
						y = event.getY();
					}
					break;

				case MotionEvent.ACTION_POINTER_DOWN:
					if (event.getPointerCount() == 2) {
						FLAG_ON_POINTER1_DOWN = true;
						mClickTime2 = System.currentTimeMillis();
						yScroll = event.getY();
					} else if (event.getPointerCount() == 3) {
						FLAG_ON_POINTER2_DOWN = true;
						mClickTime3 = System.currentTimeMillis();
					}
					break;

				case MotionEvent.ACTION_MOVE: // a pointer was moved
					if (event.getPointerCount() == 2) {
						command = MouseConstant.MOUSE_SCROLL;
						MouseScroll mouseScroll = new MouseScroll((int) ((event
								.getY() - yScroll) * FragmentControl.sScrollingSpeed));

						SenderData senderData = new SenderData();
						senderData.setCommand(command);
						senderData.setData(mouseScroll);

						mProcessSendControlCommand = new ProcessSendControlCommand(
								FragmentRemoteControl.this, senderData,
								FragmentControl.mDatagramSoc,
								FragmentControl.mConnectedServerIP);
						mProcessSendControlCommand.execute();

						yScroll = event.getY();
					} else if (event.getPointerCount() == 1) {
						command = MouseConstant.MOUSE_MOVE_COMMAND;
						Coordinates coo = new Coordinates();
						coo.setX((int) ((event.getX() - x)) * FragmentControl.sPointerSpeed);
						coo.setY((int) ((event.getY() - y)) * FragmentControl.sPointerSpeed);

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
					break;
				case MotionEvent.ACTION_UP:
					if (event.getPointerCount() == 1) {
						// nếu đang giữ ngón 2 thì ko xét
						if (FLAG_ON_POINTER1_DOWN) {
							// release pointer1
							FLAG_ON_POINTER1_DOWN = false;
							break;
						}

						mClickTime = System.currentTimeMillis() - mClickTime;
						if (mClickTime <= MAX_CLICK_DURATION) {
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
					}
					break;

				case MotionEvent.ACTION_POINTER_UP:
					if (event.getPointerCount() == 2) {
						// nếu đang giữ ngón 3 thì ko xét
						if (FLAG_ON_POINTER2_DOWN) {
							FLAG_ON_POINTER2_DOWN = false;
							break;
						}

						mClickTime2 = System.currentTimeMillis() - mClickTime2;
						if (mClickTime2 <= MAX_CLICK_DURATION) {
							command = MouseConstant.MOUSE_CLICK_COMMAND;

							MouseClick mouseClick = new MouseClick();
							mouseClick
									.setButtonIndex(MouseConstant.RIGHT_MOUSE);
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
					} else if (event.getPointerCount() == 3) {
						mClickTime3 = System.currentTimeMillis() - mClickTime3;
						if (mClickTime3 <= MAX_CLICK_DURATION) {
							command = MouseConstant.MOUSE_CLICK_COMMAND;

							MouseClick mouseClick = new MouseClick();
							mouseClick
									.setButtonIndex(MouseConstant.MIDDLE_MOUSE);
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
					}
					break;
				case MotionEvent.ACTION_CANCEL:
					break;
				default:
					break;
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
	public void closeVirtualKeyboard() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txtKeyPress.getWindowToken(), 0);
	}

	@Override
	public void onResume() {
		super.onResume();

		// hide ActionBar when display FragmentRemoteControl
		ActionBar actionBar = ((ActionBarActivity) getActivity())
				.getSupportActionBar();
		actionBar.hide();
		((MainActivity) getActivity()).disableSlidingNavigationDrawer();

		receiveMaintainedConnection();

		SenderData senderData = new SenderData();
		senderData.setCommand(SocketConstant.MAINTAIN_CONNECTION);
		senderData.setData(null);

		mProcessSendMaintainedConnection = new ProcessSendMaintainedConnection(
				FragmentRemoteControl.this, senderData,
				FragmentControl.mDatagramSoc,
				FragmentControl.mConnectedServerIP);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			mProcessSendMaintainedConnection
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			mProcessSendMaintainedConnection.execute();

		mProcessCheckMaintainedConnection = new ProcessCheckMaintainedConnection(
				FragmentRemoteControl.this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			mProcessCheckMaintainedConnection
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			mProcessCheckMaintainedConnection.execute();
	}

	@Override
	public void onPause() {
		cancelCheckMaintainedConnection();
		cancelSendMaintainedConnection();
		cancelReceiveMaintainedConnection();

		closeVirtualKeyboard();

		// show ActionBar when close FragmentRemoteControl
		((ActionBarActivity) getActivity()).getSupportActionBar().show();
		((MainActivity) getActivity()).enableSlidingNavigationDrawer();

		super.onPause();
	}

	public void cancelCheckMaintainedConnection() {
		if (mProcessCheckMaintainedConnection != null
				&& !mProcessCheckMaintainedConnection.isCancelled())
			mProcessCheckMaintainedConnection.cancel(true);
	}

	public void cancelSendMaintainedConnection() {
		if (mProcessSendMaintainedConnection != null
				&& !mProcessSendMaintainedConnection.isCancelled())
			mProcessSendMaintainedConnection.cancel(true);
	}

	public void cancelReceiveMaintainedConnection() {
		if (mProcessReceiveMaintainedConnection != null
				&& !mProcessReceiveMaintainedConnection.isCancelled())
			mProcessReceiveMaintainedConnection.cancel(true);
	}

	public void receiveMaintainedConnection() {
		mProcessReceiveMaintainedConnection = new ProcessReceiveMaintainedConnection(
				this, FragmentControl.mDatagramSoc);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			mProcessReceiveMaintainedConnection
					.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			mProcessReceiveMaintainedConnection.execute();
	}

}
