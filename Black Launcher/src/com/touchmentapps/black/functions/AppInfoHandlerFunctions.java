package com.touchmentapps.black.functions;

import java.util.ArrayList;
import java.util.List;

import com.touchmentapps.black.objects.LauncherApplicationInfo;
import com.touchmentapps.black.objects.LauncherWidgetInfo;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;

public class AppInfoHandlerFunctions {
	
	private Context mContext;
		
	public AppInfoHandlerFunctions(Context context) {
		mContext = context;
	}
	
	public ArrayList<LauncherWidgetInfo> getWidgetsList() throws Exception {
		ArrayList<LauncherWidgetInfo> mWidgets = new ArrayList<LauncherWidgetInfo>(0);
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
		List<AppWidgetProviderInfo> appWidgetInfos = appWidgetManager.getInstalledProviders();
		
		for(AppWidgetProviderInfo appWidgetInfo : appWidgetInfos) {				
			mWidgets.add(new LauncherWidgetInfo(
					appWidgetInfo.label, 
					appWidgetInfo.provider.getPackageName(), 
					mContext.getPackageManager().getResourcesForApplication(appWidgetInfo.provider.getPackageName()).getDrawable(appWidgetInfo.icon),
					(appWidgetInfo.previewImage == 0) ? mContext.getPackageManager().getResourcesForApplication(appWidgetInfo.provider.getPackageName()).getDrawable(appWidgetInfo.icon)
							:mContext.getPackageManager().getResourcesForApplication(appWidgetInfo.provider.getPackageName()).getDrawable(appWidgetInfo.previewImage),
					appWidgetInfo));			
		}	    		
		return mWidgets;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<LauncherApplicationInfo> getAppsList() throws Exception {
    	ArrayList<LauncherApplicationInfo> mApplications = new ArrayList<LauncherApplicationInfo>(0);                        
        List<ResolveInfo> packages = mContext.getPackageManager().queryIntentActivities(
        		new Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER), 0);
        
		for (ResolveInfo packageInfo : packages) {	
			if(mContext.getPackageManager().getLaunchIntentForPackage(packageInfo.activityInfo.packageName) != null) {
		     	mApplications.add(new LauncherApplicationInfo(
		    			packageInfo.activityInfo.loadLabel(mContext.getPackageManager()).toString(), 
		    			mContext.getPackageManager().getPackageInfo(packageInfo.activityInfo.packageName, 0).versionName, 
		     			packageInfo.activityInfo.packageName, 
		     			packageInfo.activityInfo.loadIcon(mContext.getPackageManager())));
			}
		}
		
        Log.i("Test", "Size " + mApplications.size() + " Packages " + packages.size());
        return mApplications;  
    }
}
