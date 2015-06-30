package com.group3.pcremote.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.group3.pcremote.FragmentControl;
import com.group3.pcremote.FragmentImageSliderPager;

public class ImageSliderPagerAdapter extends FragmentPagerAdapter {

	public ImageSliderPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public Fragment getItem(int position) {

		return new FragmentImageSliderPager(position);

	}

	@Override
	public int getCount() {
		return FragmentControl.sALTouchpadBackgroundDetail.size();
	}
}
