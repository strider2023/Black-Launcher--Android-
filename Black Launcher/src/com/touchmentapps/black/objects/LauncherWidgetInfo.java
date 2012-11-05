package com.touchmentapps.black.objects;

import android.appwidget.AppWidgetProviderInfo;
import android.graphics.drawable.Drawable;

public class LauncherWidgetInfo {

	private String mWidgetName = "";
    private String mPackageName = "";
    private Drawable mWidgetIcon = null, mWidgetPreviewImage = null;
    private AppWidgetProviderInfo mAppWidgetInfo = null;
        
	/**
	 * @param mWidgetName
	 * @param mPackageName
	 * @param mWidgetIcon
	 * @param mWidgetPreviewImage
	 * @param mAppWidgetInfo
	 */
	public LauncherWidgetInfo(String mWidgetName, String mPackageName,
			Drawable mWidgetIcon, Drawable mWidgetPreviewImage,
			AppWidgetProviderInfo mAppWidgetInfo) {
		this.mWidgetName = mWidgetName;
		this.mPackageName = mPackageName;
		this.mWidgetIcon = mWidgetIcon;
		this.mWidgetPreviewImage = mWidgetPreviewImage;
		this.mAppWidgetInfo = mAppWidgetInfo;
	}

	/**
	 * @return the mWidgetName
	 */
	public String getWidgetName() {
		return mWidgetName;
	}

	/**
	 * @return the mPackageName
	 */
	public String getPackageName() {
		return mPackageName;
	}

	/**
	 * @return the mWidgetIcon
	 */
	public Drawable getWidgetIcon() {
		return mWidgetIcon;
	}

	/**
	 * @return the mAppWidgetInfo
	 */
	public AppWidgetProviderInfo getAppWidgetInfo() {
		return mAppWidgetInfo;
	}

	/**
	 * @return the mWidgetPreviewImage
	 */
	public Drawable getWidgetPreviewImage() {
		return mWidgetPreviewImage;
	}
	
	
}
