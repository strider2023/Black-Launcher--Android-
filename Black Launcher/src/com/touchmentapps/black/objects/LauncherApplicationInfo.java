package com.touchmentapps.black.objects;

import android.graphics.drawable.Drawable;

public class LauncherApplicationInfo {
	
	private String mAppName = "";
    private String mAppVersionName = "";
    private String mPackageName = "";
    private Drawable icon;
        
	/**
	 * @param mAppName
	 * @param mAppDescription
	 * @param mAppVersionName
	 * @param mPackageName
	 * @param icon
	 */
	public LauncherApplicationInfo(String mAppName, String mAppVersionName, 
			String mPackageName, Drawable icon) {
		this.mAppName = mAppName;
		this.mAppVersionName = mAppVersionName;
		this.mPackageName = mPackageName;
		this.icon = icon;
	}

	/**
	 * @return the appname
	 */
	public String getAppname() {
		return mAppName;
	}

	/**
	 * @return the pname
	 */
	public String getPackageName() {
		return mPackageName;
	}

	/**
	 * @return the icon
	 */
	public Drawable getIcon() {
		return icon;
	}

	/**
	 * @return the mAppVersionName
	 */
	public String getAppVersionName() {
		return mAppVersionName;
	}		
}
