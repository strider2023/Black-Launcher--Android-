package com.touchmentapps.black;

import java.util.ArrayList;

import com.touchmentapps.black.actionitem.ActionItem;
import com.touchmentapps.black.actionitem.QuickAction;
import com.touchmentapps.black.functions.AppInfoHandlerFunctions;
import com.touchmentapps.black.objects.LauncherPackageInfo;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BlackLauncherFragment extends Fragment {
	
	private LinearLayout mHolderLayout;
	private TextView mAppsCounterText;
	private LayoutInflater mInflater;
	private Typeface mTypeface;
	private ArrayList<LauncherPackageInfo> mInfo;
	private AppInfoHandlerFunctions mAppInfoFunctions;
		
	private static final int ID_INFO     = 1;
	private static final int ID_DELETE   = 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.layout_launcher_all_apps_holder_fragment, null);
		mHolderLayout = (LinearLayout) fragmentView.findViewById(R.id.launcher_all_apps_holder);
		mAppsCounterText = (TextView) fragmentView.findViewById(R.id.launcher_all_apps_counter_text);
		
		mInflater = LayoutInflater.from(getActivity());
		mAppInfoFunctions = new AppInfoHandlerFunctions(getActivity());
		mTypeface = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
		
		new SetMasterDataTask().execute();
		
		return fragmentView;
	}
	
	private void launchApplication(String packageName) {
    	Intent mLaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
    	if(mLaunchIntent != null) {
    		try {
    			startActivity(mLaunchIntent);
    		} catch (ActivityNotFoundException e) {
				e.printStackTrace();
				Toast.makeText(getActivity(), "Application not found", Toast.LENGTH_LONG).show();
			}
    	}
    }
		
	private View applicationItemView(final LauncherPackageInfo packageInfo) {
    	View holderView = mInflater.inflate(R.layout.layout_launcher_application, null);
    	TextView mText = (TextView) holderView.findViewById(R.id.launcher_application_name);
		mText.setTypeface(mTypeface);
		mText.setText(packageInfo.getAppname());
		mText.setCompoundDrawablesWithIntrinsicBounds(packageInfo.getIcon(), null, null, null);

		ActionItem infoItem = new ActionItem(ID_INFO, getActivity().getResources().getString(R.string.information), null);
		ActionItem deleteItem	= new ActionItem(ID_DELETE, getActivity().getResources().getString(R.string.uninstall), null);
		final QuickAction quickAction = new QuickAction(getActivity(), QuickAction.VERTICAL);
		quickAction.addActionItem(infoItem);
		quickAction.addActionItem(deleteItem);
		
		mText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), packageInfo.getPname(), Toast.LENGTH_LONG).show();
				launchApplication(packageInfo.getPname());
			}
		});
		
		mText.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				quickAction.show(v);
				return true;
			}
		});
		
		//Set listener for action item clicked
		quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
			@Override
					public void onItemClick(QuickAction source, int pos, int actionId) {	
						switch(actionId) {
							case ID_INFO:
								break;
							case ID_DELETE:
								break;
						}
					}
				});
		
    	return holderView;
    }
	
	private class SetMasterDataTask extends AsyncTask<Void,Void,Void>{
        protected Void doInBackground(Void... params) {
        	mInfo = mAppInfoFunctions.getAppsList(false);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        	if(mInfo != null && !mInfo.isEmpty()) {
        		mAppsCounterText.setText(String.valueOf(mInfo.size()));
	        	for(LauncherPackageInfo info : mInfo ) 
	        		mHolderLayout.addView(applicationItemView(info));
        	}
        }
    }
}
