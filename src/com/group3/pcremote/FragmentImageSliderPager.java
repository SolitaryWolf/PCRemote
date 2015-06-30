package com.group3.pcremote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class FragmentImageSliderPager extends Fragment {
	private int mCurrentImagePosition = 0; // vị trí này tính từ 1
	private ImageView imgTouchpadBackground;
	private TextView tvBackgroundName;

	public FragmentImageSliderPager(int mCurrentImagePosition) {
		this.mCurrentImagePosition = mCurrentImagePosition;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_layout_imagesliderpager,
				container, false);
		getFormWidgets(rootView);

		return rootView;
	}

	private void getFormWidgets(View rootView) {
		imgTouchpadBackground = (ImageView) rootView.findViewById(R.id.imgTouchpadBackground);
		tvBackgroundName = (TextView) rootView.findViewById(R.id.tvBackgroundName);
		
		imgTouchpadBackground.setImageResource(FragmentControl.sALTouchpadBackgroundDetail.get(mCurrentImagePosition).getImgId());
		imgTouchpadBackground.setScaleType(ScaleType.FIT_XY);
		tvBackgroundName.setText(FragmentControl.sALTouchpadBackgroundDetail.get(mCurrentImagePosition).getImgName());
	}
}
