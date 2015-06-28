package com.group3.pcremote;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;

public class FragmentSetting extends Fragment {
	private Switch swMouseButton;
	private Switch swAutoRotate;
	private SeekBar sbPointerSpeed;
	private SeekBar sbScrollingSpeed;
	private Button btnChangeTouchpadBackground;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_layout_setting,
				container, false);

		getFormWidgets(rootView);
		addEventToFormWidget(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		swAutoRotate = (Switch) rootView.findViewById(R.id.swAutoRotate);
		swMouseButton = (Switch) rootView.findViewById(R.id.swMouseButton);
		sbPointerSpeed = (SeekBar) rootView.findViewById(R.id.sbPointerSpeed);
		sbScrollingSpeed = (SeekBar) rootView.findViewById(R.id.sbScrollingSpeed);
		
		swAutoRotate.setChecked(FragmentControl.sIsAutoRotateOn);
		swMouseButton.setChecked(FragmentControl.sIsMouseButtonsOn);
		sbPointerSpeed.setProgress(FragmentControl.sPointerSpeed - 1);
		sbScrollingSpeed.setProgress(FragmentControl.sScrollingSpeed - 1);
	}

	private void addEventToFormWidget(View rootView) {

	}

	@Override
	public void onResume() {
		super.onResume();

		// change navigation drawer when press back
		((MainActivity) getActivity()).changeNavigationDrawerItem(1);
	}
	
	
	@Override
	public void onPause() {
		// save setting before closing
		FragmentControl.sIsAutoRotateOn = swAutoRotate.isChecked();
		FragmentControl.sIsMouseButtonsOn = swMouseButton.isChecked();
		FragmentControl.sPointerSpeed = sbPointerSpeed.getProgress() + 1;
		FragmentControl.sScrollingSpeed = sbScrollingSpeed.getProgress() + 1;
		
		super.onPause();
	}

	private void saveSetting() {
		// method trả về SharedPreferences với đối số 1 là tên của file trạng
		// thái cần lưu(ko cần thêm đuôi vì
		// mặc định đuôi là .xml), đối số 2 là chế độ lưu
		SharedPreferences sPre = getActivity().getSharedPreferences(
				"setting", getActivity().MODE_PRIVATE);
		// tạo đối tượng Editor cho phép ta chỉnh sửa dữ liệu cần lưu
		SharedPreferences.Editor editor = sPre.edit();

		editor.putBoolean("isMouseButtonOn", FragmentControl.sIsMouseButtonsOn);
		editor.putBoolean("isAutoRotateOn", FragmentControl.sIsAutoRotateOn);
		editor.putInt("pointerSpeed", FragmentControl.sPointerSpeed);
		editor.putInt("scrollingSpeed", FragmentControl.sScrollingSpeed);

		// lưu trạng thái bằng cách gọi method commit()
		editor.commit();
	}

	@Override
	public void onDestroy() {
		// save setting by UserPreferences
		saveSetting();
		if (FragmentControl.sIsAutoRotateOn == false)
			setAutoOrientationEnabled(getActivity(), false);
		else
			setAutoOrientationEnabled(getActivity(), true);
		
		super.onDestroy();
	}
	
	public static void setAutoOrientationEnabled(Context context, boolean enabled)
    {
          Settings.System.putInt( context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }
}
