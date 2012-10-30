package com.touchmentapps.black.functions;

import java.util.ArrayList;
import java.util.List;

import com.touchmentapps.black.objects.LauncherPackageInfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class AppInfoHandlerFunctions {

	private Context mContext;
	
	public AppInfoHandlerFunctions(Context context) {
		mContext = context;
	}
	
	/**
	 * 
	 * @param getSysPackages
	 * @return
	 */
	public ArrayList<LauncherPackageInfo> getAppsList(boolean getSysPackages, String filterText) {
    	ArrayList<LauncherPackageInfo> res = new ArrayList<LauncherPackageInfo>();                
        List<ApplicationInfo> packages = mContext.getPackageManager()
        		                .getInstalledApplications(PackageManager.GET_META_DATA);
        
        if(filterText != null && filterText.length() > 0) {
			for (ApplicationInfo packageInfo : packages) {		
				if(!getSysPackages) {
					if(mContext.getPackageManager().getLaunchIntentForPackage(packageInfo.packageName) != null) {
						if(packageInfo.loadLabel(mContext.getPackageManager()).toString().startsWith(filterText))
							res.add(new LauncherPackageInfo(
									packageInfo.loadLabel(mContext.getPackageManager()).toString(), 
									packageInfo.packageName,
				            		packageInfo.loadIcon(mContext.getPackageManager())));
					}	
				} else {
					if(packageInfo.loadLabel(mContext.getPackageManager()).toString().startsWith(filterText))
						res.add(new LauncherPackageInfo(
								packageInfo.loadLabel(mContext.getPackageManager()).toString(), 
								packageInfo.packageName,
			            		packageInfo.loadIcon(mContext.getPackageManager())));
				}
			}
        } else {
        	for (ApplicationInfo packageInfo : packages) {		
				if(!getSysPackages) {
					if(mContext.getPackageManager().getLaunchIntentForPackage(packageInfo.packageName) != null) {
						res.add(new LauncherPackageInfo(
								packageInfo.loadLabel(mContext.getPackageManager()).toString(), 
								packageInfo.packageName,
				           		packageInfo.loadIcon(mContext.getPackageManager())));
					}	
				} else {
					res.add(new LauncherPackageInfo(
							packageInfo.loadLabel(mContext.getPackageManager()).toString(), 
							packageInfo.packageName,
			           		packageInfo.loadIcon(mContext.getPackageManager())));
				}
			}
        }
        Log.i("Test", "Size " + res.size() + " Packages " + packages.size());
        return res;  
    }
}
