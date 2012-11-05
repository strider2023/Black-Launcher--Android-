package com.touchmentapps.black.views;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.touchmentapps.black.R;
import com.touchmentapps.black.objects.LauncherWidgetInfo;

public class LauncherWidgetsMenuItem {
		
	//private Context mContext;
	private LauncherWidgetInfo mPackageInfo;
	private LayoutInflater mInflater;
	private RelativeLayout mSelectionLayout;
	private TextView mText;
	private ImageView mWidgteIcon, mWidgetImage;
	private View holderView;

	public LauncherWidgetsMenuItem(Context context, LauncherWidgetInfo packageInfo) {
		//this.mContext = context;
		this.mPackageInfo = packageInfo;
		mInflater = LayoutInflater.from(context);
		holderView = mInflater.inflate(R.layout.layout_launcher_widgets, null);
		mSelectionLayout = (RelativeLayout) holderView.findViewById(R.id.launcher_widget_container_layout);
		mText = (TextView) holderView.findViewById(R.id.launcher_widget_name);
		mWidgteIcon = (ImageView) holderView.findViewById(R.id.launcher_widget_icon);
		mWidgetImage = (ImageView) holderView.findViewById(R.id.launcher_widget_preview_image);
	}
	
	public View getItemView() {
		mText.setText(mPackageInfo.getWidgetName());
		mWidgteIcon.setImageDrawable(mPackageInfo.getWidgetIcon());
		mWidgetImage.setImageDrawable(mPackageInfo.getWidgetPreviewImage());
		
		mSelectionLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Launched Package", mPackageInfo.getPackageName());
			}
		});
		
		mSelectionLayout.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				return true;
			}
		});
		
    	return holderView;
	}
		
}
