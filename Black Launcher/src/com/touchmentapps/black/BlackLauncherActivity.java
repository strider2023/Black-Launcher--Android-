package com.touchmentapps.black;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class BlackLauncherActivity extends Activity {
	
	private SlidingDrawer mAppsListDrawer;
	private ImageView mDrawerArrowImage;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mAppsListDrawer = (SlidingDrawer) findViewById(R.id.launcher_all_apps_drawer);
        mDrawerArrowImage = (ImageView) findViewById(R.id.launcher_drawer_direction_icon);
        
        mAppsListDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				mDrawerArrowImage.animate().rotationBy(-180);
			}
		});
        
        mAppsListDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				mDrawerArrowImage.animate().rotationBy(180);
			}
		});
    }
}