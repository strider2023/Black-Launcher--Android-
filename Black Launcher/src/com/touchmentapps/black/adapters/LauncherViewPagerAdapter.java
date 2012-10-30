package com.touchmentapps.black.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class LauncherViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> mFragmentObjects;

	/**
	 * 
	 * @param fm
	 */
	public LauncherViewPagerAdapter(FragmentManager fm) {
		super(fm);
		mFragmentObjects = new ArrayList<Fragment>(0);
	}

	/**
	 * 
	 * @param fragmentObjects
	 */
	public void setFragmentSources(ArrayList<Fragment> fragmentObjects) {
		this.mFragmentObjects = fragmentObjects;
	}

	@Override
	public Fragment getItem(int position) {
		return mFragmentObjects.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentObjects.size();
	}
}
