package com.touchmentapps.black;

import java.util.ArrayList;
import java.util.List;

import com.touchmentapps.black.dragview.DeleteZone;
import com.touchmentapps.black.dragview.DragController.DragListener;
import com.touchmentapps.black.dragview.DragLayer;
import com.touchmentapps.black.dragview.DragSource;
import com.touchmentapps.black.dragview.LauncherGridAdapter;
import com.touchmentapps.black.dragview.LauncherGridItem;
import com.touchmentapps.black.objects.LauncherApplicationInfo;
import com.touchmentapps.black.objects.LauncherWidgetInfo;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class BlackHomeScreenFragement extends Fragment {
    
	private final int APPWIDGET_HOST_ID = 2048;
	private AppWidgetHost mAppWidgetHost;
	private View mHolderLayout;
	private LinearLayout mHomeScreen;
	private TextView mHomeSearchBar;
	
	private GridView grid;
	private ArrayList<LauncherGridItem> items;
	private DeleteZone deleteZone;
	private DragLayer dragLayer;
	private LauncherGridAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAppWidgetHost = new AppWidgetHost(getActivity(), APPWIDGET_HOST_ID);
		mHolderLayout = inflater.inflate(R.layout.layout_home_screen, null);
		mHomeScreen = (LinearLayout) mHolderLayout.findViewById(R.id.home_sreen_container_layout);
		mHomeSearchBar = (TextView) mHolderLayout.findViewById(R.id.home_screen_search_bar);
		deleteZone = (DeleteZone) mHolderLayout.findViewById(R.id.delete_zone_view);
		dragLayer = (DragLayer) mHolderLayout.findViewById(R.id.drag_layer);
		grid = (GridView) mHolderLayout.findViewById(R.id.image_grid_view);
				
		mHomeSearchBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PackageManager pm = getActivity().getPackageManager();
				List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_WEB_SEARCH), 0);
				 if (activities.size() == 0) {
				    Toast.makeText(getActivity(), "Search application missing!", Toast.LENGTH_LONG).show();
				  } else {
					  getActivity().startActivity(new Intent(RecognizerIntent.ACTION_WEB_SEARCH));
				  }
			}
		});
		/*if (getActivity().getScreenOrientation() == 1)
			grid.setNumColumns(3);
		else
			grid.setNumColumns(4);*/

		items = new ArrayList<LauncherGridItem>(0);
		
		adapter = new LauncherGridAdapter(getActivity(), grid, dragLayer, deleteZone);
		adapter.setDragListener(new DragListener() {
			@Override
			public void onDragStart(DragSource source, Object info, int dragAction) {
				deleteZone.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onDragEnd() {
				deleteZone.setVisibility(View.GONE);
			}
		});
		
		adapter.setEditable(true); // change this to false to disable drag & drop.
		grid.setAdapter(adapter);
				
		return mHolderLayout;
	}
	
	@Override
    public void onStart() {
        super.onStart();
        mAppWidgetHost.startListening();
    }
	
	public void addApplication(LauncherApplicationInfo mAppInfo) {		
			Intent mApplicationIntent = getActivity().getPackageManager().getLaunchIntentForPackage(mAppInfo.getPackageName());
			mApplicationIntent.putExtra("Param1", "Activity " + mAppInfo.getAppname());
			items.add(new LauncherGridItem(mAppInfo.getIcon(), mAppInfo.getAppname(),mApplicationIntent));		
			adapter.notifyDataSetChanged();
			adapter.setItems(items);
			grid.setAdapter(adapter);
	}
		
	public void addWidget(LauncherWidgetInfo mWidgetInfo) {
		Log.i("Package Name From Widget Info", mWidgetInfo.getAppWidgetInfo().provider.getPackageName());	
		int appWidgetId = mAppWidgetHost.allocateAppWidgetId();
		
		if(mWidgetInfo.getAppWidgetInfo().configure != null) {
			Intent configureIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            configureIntent.setComponent(mWidgetInfo.getAppWidgetInfo().configure);
            configureIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            if (configureIntent != null) {
                try {
                    startActivityForResult(configureIntent, 20);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
		} else {
			AppWidgetHostView hostView = mAppWidgetHost.createView(getActivity(), appWidgetId, mWidgetInfo.getAppWidgetInfo());
			hostView.setAppWidget(appWidgetId, mWidgetInfo.getAppWidgetInfo());
			hostView.setLayoutParams(new LayoutParams(240, 240));
			Log.i("Widget View", hostView.getHeight() + " Width: " + hostView.getWidth());
			mHomeScreen.addView(hostView, 240, 240);
		}
	}
}
