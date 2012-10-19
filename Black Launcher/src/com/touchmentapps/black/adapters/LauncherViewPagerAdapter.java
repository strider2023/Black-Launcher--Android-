package com.touchmentapps.black.adapters;

import java.util.ArrayList;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class LauncherViewPagerAdapter extends PagerAdapter {

	private ArrayList<View> mViewsData;
	
	public LauncherViewPagerAdapter() {
		mViewsData = new ArrayList<View>(0);
	}
	
	/**
	 * 
	 * @param details
	 */
	public void setViews(ArrayList<View> details) {
		mViewsData.addAll(details);
	}
	
	@Override
	public void destroyItem(View view, int pos, Object obj) {
		((ViewPager) view).removeView((View) obj);
	}

	@Override
	public void finishUpdate(View arg0) {
		
	}

	@Override
	public int getCount() {
		return mViewsData.size();
	}

	@Override
	public Object instantiateItem(View container, int position) {		
		((ViewPager) container).addView(mViewsData.get(position), 0);
		return mViewsData.get(position);
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == ((View) obj);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		
	}
}
