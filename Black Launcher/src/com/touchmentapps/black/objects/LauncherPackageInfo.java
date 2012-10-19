package com.touchmentapps.black.objects;

import android.graphics.drawable.Drawable;

public class LauncherPackageInfo {
	
	private String appname = "";
    private String pname = "";
    private Drawable icon;
        
	/**
	 * @param appname
	 * @param pname
	 * @param icon
	 */
	public LauncherPackageInfo(String appname, String pname,
			Drawable icon) {
		this.appname = appname;
		this.pname = pname;
		this.icon = icon;
	}

	/**
	 * @return the appname
	 */
	public String getAppname() {
		return appname;
	}

	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * @return the icon
	 */
	public Drawable getIcon() {
		return icon;
	}
}
