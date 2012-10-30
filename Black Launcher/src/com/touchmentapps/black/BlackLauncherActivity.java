package com.touchmentapps.black;

import java.util.ArrayList;

import com.touchmentapps.black.adapters.LauncherViewPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * 
 * @author Arindam Nath
 * 
 */
public class BlackLauncherActivity extends FragmentActivity {
	
	private ViewPager mLauncherViewHolder;
	private ArrayList<Fragment> mFragmentObjects = new ArrayList<Fragment>(0);
	private LauncherViewPagerAdapter mFragmentAdapter;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mFragmentAdapter = new LauncherViewPagerAdapter(getSupportFragmentManager());
        
        mLauncherViewHolder = (ViewPager) findViewById(R.id.launcher_home_view_pager);
        
        mFragmentObjects.add(new BlackHomeScreenFragement());
        mFragmentObjects.add(new BlackAllAppsMenuFragment());
                
        mFragmentAdapter.setFragmentSources(mFragmentObjects);
        mLauncherViewHolder.setAdapter(mFragmentAdapter);
    }
}