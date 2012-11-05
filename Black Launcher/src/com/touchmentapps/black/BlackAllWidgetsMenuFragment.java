package com.touchmentapps.black;

import java.util.ArrayList;

import com.touchmentapps.black.adapters.LauncherWidgetGridAdapter;
import com.touchmentapps.black.functions.AppInfoHandlerFunctions;
import com.touchmentapps.black.objects.LauncherWidgetInfo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SlidingDrawer;
import android.widget.TextView;

public class BlackAllWidgetsMenuFragment extends Fragment {
	
	private ProgressBar mLoaderProgress;
	private SlidingDrawer mQuickActionsDrawer;
	private GridView mWidgetsHolderLayout;
	private TextView mHeaderText;
	private ArrayList<LauncherWidgetInfo> mInfo;
	private AppInfoHandlerFunctions mAppInfoFunctions;
	private LauncherWidgetGridAdapter mAdapter;
	private OnWidgetSelectedListener mCallback;
	
	public interface OnWidgetSelectedListener {
        public void onWidgetSelected(LauncherWidgetInfo selectedItem);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.layout_launcher_all_apps_holder_fragment, null);
		mWidgetsHolderLayout = (GridView) fragmentView.findViewById(R.id.launcher_all_widgets_holder);
		mWidgetsHolderLayout.setVisibility(GridView.VISIBLE);
		mQuickActionsDrawer = (SlidingDrawer) fragmentView.findViewById(R.id.launcher_all_apps_quick_actions_drawer);
		mQuickActionsDrawer.setVisibility(SlidingDrawer.INVISIBLE);
		mHeaderText = (TextView) fragmentView.findViewById(R.id.launcher_all_apps_screen_header_text);
		mLoaderProgress = (ProgressBar) fragmentView.findViewById(R.id.launcher_all_apps_loading_progress_bar);
		
		mAdapter = new LauncherWidgetGridAdapter(getActivity());		
		mAppInfoFunctions = new AppInfoHandlerFunctions(getActivity());
		mHeaderText.setText(getActivity().getResources().getString(R.string.widgets));
		new SetWidgetsData().execute();
		
		mWidgetsHolderLayout.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mCallback.onWidgetSelected(mAdapter.getItem(arg2));
			}
		});
						
		return fragmentView;
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnWidgetSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnHeadlineSelectedListener");
        }
    }
		
	private class SetWidgetsData extends AsyncTask<Void,Void,Void>{
		
		public SetWidgetsData() {
			mLoaderProgress.setVisibility(ProgressBar.VISIBLE);
		}
		
        protected Void doInBackground(Void... params) {
        	try {
				mInfo = mAppInfoFunctions.getWidgetsList();
			} catch (Exception e) {
				e.printStackTrace();
			}
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        	if(mInfo != null && !mInfo.isEmpty()) {
        		mLoaderProgress.setVisibility(ProgressBar.GONE);
        		mAdapter.setData(mInfo);
        		mWidgetsHolderLayout.setAdapter(mAdapter);
        	}
        }
    }
}
