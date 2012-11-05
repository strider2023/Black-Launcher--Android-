package com.touchmentapps.black;

import java.util.ArrayList;

import com.touchmentapps.black.functions.AppInfoHandlerFunctions;
import com.touchmentapps.black.objects.LauncherApplicationInfo;
import com.touchmentapps.black.views.LauncherMenuItem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;

/**
 * 
 * @author Arindam Nath
 *
 */
public class BlackAllAppsMenuFragment extends Fragment {
	
	private ProgressBar mLoaderProgress;
	private CheckBox mSearchListButton;
	private ImageButton mSettingsButton;
	private SlidingDrawer mQuickActionsDrawer;
	private ImageView mQuickActionsDrawerArrow;
	private EditText mSearchEdittext;
	private LinearLayout mHolderLayout;
	private TextView mHeaderText, mHeaderCountText;
	private ArrayList<LauncherApplicationInfo> mInfo;
	private AppInfoHandlerFunctions mAppInfoFunctions;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.layout_launcher_all_apps_holder_fragment, null);
		mHolderLayout = (LinearLayout) fragmentView.findViewById(R.id.launcher_all_apps_holder);
		mHeaderText = (TextView) fragmentView.findViewById(R.id.launcher_all_apps_screen_header_text);
		mHeaderCountText = (TextView) fragmentView.findViewById(R.id.launcher_all_apps_screen_header_count_text);
		mSearchListButton = (CheckBox) fragmentView.findViewById(R.id.launcher_all_apps_search_btn);
		mSearchEdittext = (EditText) fragmentView.findViewById(R.id.launcher_all_apps_search_textview);
		mLoaderProgress = (ProgressBar) fragmentView.findViewById(R.id.launcher_all_apps_loading_progress_bar);
		mQuickActionsDrawer = (SlidingDrawer) fragmentView.findViewById(R.id.launcher_all_apps_quick_actions_drawer);
		mQuickActionsDrawerArrow = (ImageView) fragmentView.findViewById(R.id.quick_actions_drawer_handle_image);
		mSettingsButton = (ImageButton) fragmentView.findViewById(R.id.launcher_all_apps_settings_btn);
		mHolderLayout.setVisibility(LinearLayout.VISIBLE);
		mHeaderText.setText(getActivity().getResources().getString(R.string.apps));
		
		mAppInfoFunctions = new AppInfoHandlerFunctions(getActivity());
		new FetchAppsList().execute();
		
		mSearchListButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					mSearchEdittext.setVisibility(EditText.VISIBLE);
					mHeaderCountText.setVisibility(TextView.VISIBLE);
				} else {
					mHeaderCountText.setVisibility(TextView.GONE);
					mSearchEdittext.setVisibility(EditText.GONE);
					mSearchEdittext.getText().clear();
				}
			}
		});
		
		mSearchEdittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				new FilterAppsList(s.toString()).execute();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) { }
			
			@Override
			public void afterTextChanged(Editable s) { }
		});
		
		mSettingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
			}
		});
		
		mQuickActionsDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				mQuickActionsDrawerArrow.animate().rotationX(180);
			}
		});
		
		mQuickActionsDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				mQuickActionsDrawerArrow.animate().rotationX(0);
				mSearchListButton.setChecked(false);
			}
		});
		
		return fragmentView;
	}
	
	private class FilterAppsList extends AsyncTask<Void,Void,Void>{
		
		private ArrayList<LauncherApplicationInfo> mFilteredList;
		private String filter = "";
		
		public FilterAppsList(String filterText) {
			filter = filterText;
			mFilteredList = new ArrayList<LauncherApplicationInfo>(0);
			mHolderLayout.removeAllViews();
			mLoaderProgress.setVisibility(ProgressBar.VISIBLE);
		}
		
        protected Void doInBackground(Void... params) {
        	try {
        		if(mInfo != null && !mInfo.isEmpty()) {
        			for(LauncherApplicationInfo appInfo : mInfo) 
        				if(appInfo.getAppname().toLowerCase().startsWith(filter.toLowerCase())) 
        					mFilteredList.add(appInfo);
        		}
			} catch (Exception e) {
				e.printStackTrace();
			}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        	if(mFilteredList != null && !mFilteredList.isEmpty()) {
        		mLoaderProgress.setVisibility(ProgressBar.GONE);
        		mHeaderCountText.setText(String.valueOf(mFilteredList.size()));
	        	for(LauncherApplicationInfo info : mFilteredList ) 
	        		mHolderLayout.addView(new LauncherMenuItem(getActivity(), info).getItemView());
        	}
        }
    }
		
	private class FetchAppsList extends AsyncTask<Void,Void,Void>{
		
		public FetchAppsList() {
			mHolderLayout.removeAllViews();
			mLoaderProgress.setVisibility(ProgressBar.VISIBLE);
		}
		
        protected Void doInBackground(Void... params) {
        	try {
				mInfo = mAppInfoFunctions.getAppsList();
			} catch (Exception e) {
				e.printStackTrace();
			}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        	if(mInfo != null && !mInfo.isEmpty()) {
        		mHeaderCountText.setText(String.valueOf(mInfo.size()));
        		mLoaderProgress.setVisibility(ProgressBar.GONE);
	        	for(LauncherApplicationInfo info : mInfo ) 
	        		mHolderLayout.addView(new LauncherMenuItem(getActivity(), info).getItemView());
        	}
        }
    }
}
