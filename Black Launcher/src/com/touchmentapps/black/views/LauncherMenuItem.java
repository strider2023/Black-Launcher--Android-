package com.touchmentapps.black.views;

import com.touchmentapps.black.R;
import com.touchmentapps.black.actionitem.ActionItem;
import com.touchmentapps.black.actionitem.QuickAction;
import com.touchmentapps.black.actionitem.QuickAction.OnDismissListener;
import com.touchmentapps.black.objects.LauncherApplicationInfo;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	private final int ID_PIN_TO_START_MENU = 3;
	private final int ID_SET_APP_PASSWORD = 4;
	private final int ID_SET_PASSWORD = 5;
	private final int ID_REMOVE_PASSWORD = 6;
	private final String SCHEME = "package";
	
	private LauncherApplicationInfo mPackageInfo;
	private LayoutInflater mInflater;
	private LinearLayout mHolderLayout;
	private TextView mText;
	private ImageView mIcon;
	private View holderView;
	private Context mContext;
	
	private ActionItem infoItem, deleteItem, pinItem, passwordItem;
	private QuickAction quickAction;

	public LauncherMenuItem(Context context, LauncherApplicationInfo packageInfo) {
		this.mContext = context;
		this.mPackageInfo = packageInfo;
		mInflater = LayoutInflater.from(context);
		holderView = mInflater.inflate(R.layout.layout_launcher_application, null);
		mText = (TextView) holderView.findViewById(R.id.launcher_application_name);
		mIcon = (ImageView) holderView.findViewById(R.id.launcher_application_icon);
		mHolderLayout = (LinearLayout) holderView.findViewById(R.id.launcher_application_container_layout);
		/** Init the Quick Action view. **/
		pinItem = new ActionItem(ID_PIN_TO_START_MENU, context.getResources().getString(R.string.pin_to_start), null);
		infoItem = new ActionItem(ID_INFO, context.getResources().getString(R.string.information), null);
		passwordItem = new ActionItem(ID_SET_APP_PASSWORD, context.getResources().getString(R.string.set_passowrd), null);
		deleteItem	= new ActionItem(ID_UNINSTALL, context.getResources().getString(R.string.uninstall), null);
		quickAction = new QuickAction(context, QuickAction.VERTICAL);
		quickAction.addActionItem(pinItem);
		quickAction.addActionItem(infoItem);
		quickAction.addActionItem(passwordItem);
		quickAction.addActionItem(deleteItem);
	}
	
	public View getItemView() {
		mText.setText(mPackageInfo.getAppname());
		mIcon.setImageDrawable(mPackageInfo.getIcon());

		mHolderLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Launched Package", mPackageInfo.getPackageName());
				launchApplication(mPackageInfo.getPackageName());
			}
		});
		
		mHolderLayout.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				quickAction.show(v);
				mHolderLayout.animate().setDuration(0);
				mHolderLayout.animate().translationXBy(20);
				return true;
			}
		});
		
		quickAction.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mHolderLayout.animate().setDuration(0);
				mHolderLayout.animate().translationXBy(-20);
			}
		});
		
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
			@Override
			public void onItemClick(QuickAction source, int pos, int actionId) {	
				switch(actionId) {
				case ID_PIN_TO_START_MENU:
					quickAction.dismiss();
					break;
				case ID_INFO:
					quickAction.dismiss();
					getAppInfo(mPackageInfo.getPackageName());
					break;
				case ID_SET_APP_PASSWORD:
					quickAction.dismiss();
					setPassword(mPackageInfo, ID_SET_PASSWORD);
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
	
	private void getAppInfo(String packageName) {
		Intent mAppInfoIntent = new Intent();
		mAppInfoIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        mAppInfoIntent.setData(Uri.fromParts(SCHEME, packageName, null));
        mContext.startActivity(mAppInfoIntent);
	}
		
	private void setPassword(final LauncherApplicationInfo packageInfo, int executionID) {
		final AlertDialog mPasswordDialog = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT).create();
		mPasswordDialog.setCanceledOnTouchOutside(false);
		mPasswordDialog.setIcon(mPackageInfo.getIcon());
		mPasswordDialog.setTitle(mPackageInfo.getAppname());
		
		EditText mPasswordText = new EditText(mContext);
		mPasswordText.setSingleLine(true);
		mPasswordText.setTextColor(Color.BLACK);
		mPasswordText.setImeOptions(EditorInfo.IME_ACTION_DONE);
		mPasswordText.setTransformationMethod(new PasswordTransformationMethod());
		mPasswordText.setHint(R.string.enter_password);
		mPasswordDialog.setView(mPasswordText);
		
		mPasswordDialog.setButton(mContext.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mPasswordDialog.dismiss();
			}
		});
		
		switch (executionID) {
		case ID_SET_PASSWORD:
			mPasswordDialog.setButton2(mContext.getResources().getString(R.string.set_passowrd), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mPasswordDialog.dismiss();
				}
			});
			break;
		case ID_REMOVE_PASSWORD:
			mPasswordDialog.setButton2(mContext.getResources().getString(R.string.remove_password), new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					mPasswordDialog.dismiss();
				}
			});
			break;
		}
		
		mPasswordDialog.show();
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
