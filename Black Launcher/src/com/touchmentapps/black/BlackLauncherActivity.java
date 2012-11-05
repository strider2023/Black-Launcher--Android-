package com.touchmentapps.black;

import java.util.ArrayList;

import com.touchmentapps.black.adapters.LauncherViewPagerAdapter;
import com.touchmentapps.black.objects.LauncherWidgetInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * 
 * @author Arindam Nath
 * 
 */
public class BlackLauncherActivity extends FragmentActivity implements BlackAllWidgetsMenuFragment.OnWidgetSelectedListener {
	
	private static final int REQUEST_PICK_WALLPAPER = 10;
	
	private ViewPager mLauncherViewHolder;
	private ArrayList<Fragment> mFragmentObjects = new ArrayList<Fragment>(0);
	private LauncherViewPagerAdapter mFragmentAdapter;
	private BlackHomeScreenFragement mHomeScreen;
	private BlackAllAppsMenuFragment mAppsMenu;
	private BlackAllWidgetsMenuFragment mWidgetsMenu;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        mLauncherViewHolder = (ViewPager) findViewById(R.id.launcher_home_view_pager);
        mFragmentAdapter = new LauncherViewPagerAdapter(getSupportFragmentManager());
        mHomeScreen = new BlackHomeScreenFragement();
        mAppsMenu = new BlackAllAppsMenuFragment();
        mWidgetsMenu = new BlackAllWidgetsMenuFragment();
        
        mFragmentObjects.add(mHomeScreen);
        mFragmentObjects.add(mAppsMenu);
        mFragmentObjects.add(mWidgetsMenu);
                
        mFragmentAdapter.setFragmentSources(mFragmentObjects);
        mLauncherViewHolder.setAdapter(mFragmentAdapter);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_app_main, menu);
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_set_wallpaper:
		        Intent mWallpaperChooser = Intent.createChooser(new Intent(Intent.ACTION_SET_WALLPAPER), getText(R.string.choose_wallpaper));
		        startActivityForResult(mWallpaperChooser, REQUEST_PICK_WALLPAPER);
				break;
			case R.id.action_manage_apps:
				startActivity(new Intent().setAction(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)); 
				break;
			case R.id.action_system_settings:
				startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				break;
		}
		return true;
	}

	@Override
	public void onWidgetSelected(LauncherWidgetInfo selectedItem) {
		Log.i("Package Name", selectedItem.getPackageName());			
		mHomeScreen.addWidget(selectedItem);
	}
}