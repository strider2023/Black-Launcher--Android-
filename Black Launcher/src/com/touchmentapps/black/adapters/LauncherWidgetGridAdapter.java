package com.touchmentapps.black.adapters;

import java.util.ArrayList;

import com.touchmentapps.black.R;
import com.touchmentapps.black.objects.LauncherWidgetInfo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LauncherWidgetGridAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private ArrayList<LauncherWidgetInfo> mWidgetData = new ArrayList<LauncherWidgetInfo>(0);

	public LauncherWidgetGridAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}
	
	public void setData(ArrayList<LauncherWidgetInfo> data) {
		mWidgetData.clear();
		mWidgetData.addAll(data);
	}
	
	@Override
	public int getCount() {
		Log.i("Count", "Size: " + mWidgetData.size());
		return mWidgetData.size();
	}

	@Override
	public LauncherWidgetInfo getItem(int position) {
		return mWidgetData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_launcher_widgets, null);		
			holder.mText = (TextView) convertView.findViewById(R.id.launcher_widget_name);
			holder.mWidgteIcon = (ImageView) convertView.findViewById(R.id.launcher_widget_icon);
			holder.mWidgetImage = (ImageView) convertView.findViewById(R.id.launcher_widget_preview_image);
			//Set the data
			holder.mText.setText(mWidgetData.get(position).getWidgetName());
			holder.mWidgteIcon.setImageDrawable(mWidgetData.get(position).getWidgetIcon());
			holder.mWidgetImage.setImageDrawable(mWidgetData.get(position).getWidgetPreviewImage());
		} else {
			holder = new ViewHolder();
			RelativeLayout holderView = (RelativeLayout) convertView;
			holder.mWidgetImage = (ImageView) holderView.getChildAt(0);
			LinearLayout mInfoLayout = (LinearLayout) holderView.getChildAt(1);
			holder.mWidgteIcon = (ImageView) mInfoLayout.getChildAt(0);
			holder.mText = (TextView) mInfoLayout.getChildAt(1);
			//Set the data
			holder.mText.setText(mWidgetData.get(position).getWidgetName());
			holder.mWidgteIcon.setImageDrawable(mWidgetData.get(position).getWidgetIcon());
			holder.mWidgetImage.setImageDrawable(mWidgetData.get(position).getWidgetPreviewImage());
			return convertView;
		}
		
		return convertView;
	}

	static class ViewHolder {
		private TextView mText;
		private ImageView mWidgteIcon, mWidgetImage;
	}
}
