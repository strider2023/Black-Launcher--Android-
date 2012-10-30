package com.touchmentapps.black;

import java.util.ArrayList;

import com.touchmentapps.black.functions.AppInfoHandlerFunctions;
import com.touchmentapps.black.objects.LauncherPackageInfo;
import com.touchmentapps.black.views.LauncherMenuItem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * @author Arindam Nath
 *
 */
public class BlackAllAppsMenuFragment extends Fragment {
	
	private ProgressBar mLoaderProgress;
	private CheckBox mSearchListButton;
	private EditText mSearchEdittext;
	private LinearLayout mHolderLayout;
	private TextView mAppsCounterText;
	private ArrayList<LauncherPackageInfo> mInfo;
	private AppInfoHandlerFunctions mAppInfoFunctions;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.layout_launcher_all_apps_holder_fragment, null);
		mHolderLayout = (LinearLayout) fragmentView.findViewById(R.id.launcher_all_apps_holder);
		mAppsCounterText = (TextView) fragmentView.findViewById(R.id.launcher_all_apps_counter_text);
		mSearchListButton = (CheckBox) fragmentView.findViewById(R.id.launcher_all_apps_search_btn);
		mSearchEdittext = (EditText) fragmentView.findViewById(R.id.launcher_all_apps_search_textview);
		mLoaderProgress = (ProgressBar) fragmentView.findViewById(R.id.launcher_all_apps_loading_progress_bar);
				
		mAppInfoFunctions = new AppInfoHandlerFunctions(getActivity());
		new SetMasterDataTask().execute();
		
		mSearchListButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					mSearchEdittext.setVisibility(EditText.VISIBLE);
				} else {
					mSearchEdittext.setVisibility(EditText.GONE);
					mSearchEdittext.getText().clear();
				}
			}
		});
		
		mSearchEdittext.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				new SetMasterDataTask().execute();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) { }
			
			@Override
			public void afterTextChanged(Editable s) { }
		});
		
		return fragmentView;
	}
		
	private class SetMasterDataTask extends AsyncTask<Void,Void,Void>{
		
		public SetMasterDataTask() {
			mHolderLayout.removeAllViews();
			mLoaderProgress.setVisibility(ProgressBar.VISIBLE);
		}
		
        protected Void doInBackground(Void... params) {
        	mInfo = mAppInfoFunctions.getAppsList(false, mSearchEdittext.getText().toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        	if(mInfo != null && !mInfo.isEmpty()) {
        		mLoaderProgress.setVisibility(ProgressBar.GONE);
        		mAppsCounterText.setText(String.valueOf(mInfo.size()));
	        	for(LauncherPackageInfo info : mInfo ) 
	        		mHolderLayout.addView(new LauncherMenuItem(getActivity(), info).getItemView());
        	}
        }
    }
}
