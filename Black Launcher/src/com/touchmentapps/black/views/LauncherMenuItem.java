package com.touchmentapps.black.views;

import com.touchmentapps.black.R;
import com.touchmentapps.black.actionitem.ActionItem;
import com.touchmentapps.black.actionitem.QuickAction;
import com.touchmentapps.black.actionitem.QuickAction.OnDismissListener;
import com.touchmentapps.black.objects.LauncherPackageInfo;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author Arindam Nath
 *
 */
public class LauncherMenuItem {
	
	private final int ID_INFO = 1;
	private final int ID_UNINSTALL = 2;
	private final String SCHEME = "package";
	
	private LauncherPackageInfo mPackageInfo;
	private LayoutInflater mInflater;
	private TextView mText;
	private View holderView;
	private Context mContext;
	
	private ActionItem infoItem, deleteItem;
	private QuickAction quickAction;

	public LauncherMenuItem(Context context, LauncherPackageInfo packageInfo) {
		this.mContext = context;
		this.mPackageInfo = packageInfo;
		mInflater = LayoutInflater.from(context);
		holderView = mInflater.inflate(R.layout.layout_launcher_application, null);
		mText = (TextView) holderView.findViewById(R.id.launcher_application_name);
		/** Init the Quick Action view. **/
		infoItem = new ActionItem(ID_INFO, context.getResources().getString(R.string.information), null);
		deleteItem	= new ActionItem(ID_UNINSTALL, context.getResources().getString(R.string.uninstall), null);
		quickAction = new QuickAction(context, QuickAction.VERTICAL);
		quickAction.addActionItem(infoItem);
		quickAction.addActionItem(deleteItem);
	}
	
	public View getItemView() {
		mText.setText(mPackageInfo.getAppname());
		mText.setCompoundDrawablesWithIntrinsicBounds(mPackageInfo.getIcon(), null, null, null);

		mText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Launched Package", mPackageInfo.getPackageName());
				launchApplication(mPackageInfo.getPackageName());
			}
		});
		
		mText.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				quickAction.show(v);
				mText.animate().setDuration(0);
				mText.animate().translationXBy(20);
				return true;
			}
		});
		
		quickAction.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mText.animate().setDuration(0);
				mText.animate().translationXBy(-20);
			}
		});
		
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {	
				switch(actionId) {
					case ID_INFO:
						quickAction.dismiss();
						infoApplication(mPackageInfo.getPackageName());
						break;
					case ID_UNINSTALL:
						quickAction.dismiss();
						uninstallApplication(mPackageInfo.getPackageName()); 
						break;
				}
			}
		});
		
    	return holderView;
	}
	
	private void infoApplication(String packageName) {		
		
	}
	
	/**
	 * 
	 * @param packageName
	 */
	private void uninstallApplication(String packageName) {
		Intent mUninstallIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
		Uri packageURI = Uri.fromParts(SCHEME, packageName, null); 
		if(mUninstallIntent != null) {
			try {
				mContext.startActivity(new Intent(Intent.ACTION_DELETE, packageURI));
    		} catch (ActivityNotFoundException e) {
				e.printStackTrace();
				Toast.makeText(mContext, 
						mContext.getResources().getString(R.string.package_not_found_error), 
						Toast.LENGTH_LONG).show();
			}
		} else 
			Toast.makeText(mContext, 
					mContext.getResources().getString(R.string.package_not_found_error), 
					Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 
	 * @param packageName
	 */
	private void launchApplication(String packageName) {
    	Intent mLaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
    	if(mLaunchIntent != null) {
    		try {
    			mContext.startActivity(mLaunchIntent);
    		} catch (ActivityNotFoundException e) {
				e.printStackTrace();
				Toast.makeText(mContext, 
						mContext.getResources().getString(R.string.package_not_found_error), 
						Toast.LENGTH_LONG).show();
			}
    	} else 
    		Toast.makeText(mContext, 
    				mContext.getResources().getString(R.string.package_not_found_error), 
    				Toast.LENGTH_LONG).show();
    }
}
