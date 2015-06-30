package com.group3.pcremote;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.group3.pcremote.adapter.ImageSliderPagerAdapter;

public class ImageSliderActivity extends FragmentActivity {
	private ImageSliderPagerAdapter mImageSliderPagerAdapter = null;
	private ViewPager mPager;
	private int mCurrentImagePosition = 0;

	private Button btnOK;
	private Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_slider);

		getFormWidgets();
		addEventToFormWidgets();
	}

	private void getFormWidgets() {
		mPager = (ViewPager) findViewById(R.id.imagePager);
		mImageSliderPagerAdapter = new ImageSliderPagerAdapter(
				getSupportFragmentManager());
		mPager.setAdapter(mImageSliderPagerAdapter);

		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnOK = (Button) findViewById(R.id.btnOK);

		for (int i = 0; i < FragmentControl.sALTouchpadBackgroundDetail.size(); i++) {
			if (FragmentControl.sALTouchpadBackgroundDetail.get(i).getImgName()
					.equals(FragmentControl.sTouchpadBackground)) {
				mCurrentImagePosition = i;
				break;
			}
		}
		mPager.setCurrentItem(mCurrentImagePosition);
	}

	private void addEventToFormWidgets() {
		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnOK.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentControl.sTouchpadBackground = FragmentControl.sALTouchpadBackgroundDetail
						.get(mPager.getCurrentItem()).getImgName();
				finish();
			}
		});
	}
}
