package com.touchmentapps.black.dragview;

import java.util.ArrayList;
import java.util.List;

import com.touchmentapps.black.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class LauncherGridAdapter extends BaseAdapter implements
		OnLongClickListener, OnClickListener, DragListener,
		DeleteLaunchItemHandler {

	public static final String LAUNCHER_GRID_LOG = "LauncherGridLog";
	
	private Context mContext;
	private LayoutInflater inflater;

	// @JsonIgnore
	// private ViewHolder holder;

	private List<LauncherGridItem> items = new ArrayList<LauncherGridItem>(0);

	@SuppressWarnings("unused")
	private GridView iconGrid;
	private DragController dragController;
	private DragLayer dragLayer;
	private DeleteZone deleteZone;

	private boolean editable = true;

	static class ViewHolder {
		public ImageView image;
		public TextView text;
	}

	public LauncherGridAdapter(Context c, GridView iconGrid, DragLayer dragLayer) {
		this(c, iconGrid, dragLayer, null);
	}

	public LauncherGridAdapter(Context c, GridView iconGrid, DragLayer dragLayer, DeleteZone deleteZone) {
		mContext = c;
		this.iconGrid = iconGrid;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		dragController = new DragController(mContext);
		this.dragLayer = dragLayer;
		this.dragLayer.setDragController(dragController);
		this.dragLayer.setGridView(iconGrid);

		if (deleteZone != null) {
			this.deleteZone = deleteZone;
			this.deleteZone.setOnItemDeleted(this);
			this.deleteZone.setEnabled(true);
			this.dragLayer.setDeleteZoneId(deleteZone.getId());
		}

		dragController.setDragListener(dragLayer);
	}

	@Override
	public int getCount() {
		return getItems().size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_view_item_layout, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.selection_item_image);
			holder.text = (TextView) convertView.findViewById(R.id.selection_item_text);
			
			holder.image.setScaleType(ScaleType.CENTER_INSIDE);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		DraggableRelativeLayout layout = (DraggableRelativeLayout) convertView;
		convertView.setOnLongClickListener(this);
		convertView.setOnClickListener(this);
		LauncherGridItem item = getItems().get(position);
		layout.setItem(item);
		layout.setDragListener(this);
		Drawable d = holder.image.getDrawable();
		d.setCallback(null);
		holder.image.setImageDrawable(null);
		if(item.getImageDrawable() == null)
			holder.image.setImageResource(R.drawable.ic_launcher);
		else 
			holder.image.setImageDrawable(item.getImageDrawable());
				
		holder.text.setText(item.getCaption());
		layout.setImage(holder.image);
		layout.setText(holder.text);
		layout.setItem(item);
		layout.canDelete(item.canDelete());
		return convertView;
	}

	public void setItems(List<LauncherGridItem> items) {
		this.items = items;
	}
	
	public List<LauncherGridItem> getItems() {
		return items;
	}

	public boolean startDrag(View v) {
		DragSource dragSource = (DragSource) v;
		// We are starting a drag. Let the DragController handle it.
		dragController.startDrag(v, dragSource, dragSource,
				DragController.DRAG_ACTION_MOVE);
		return true;
	}

	@Override
	public boolean onLongClick(View v) {
		if (!v.isInTouchMode() || !isEditable()) {
			return false;
		}
		Log.d(LAUNCHER_GRID_LOG, "Drag started");
		return startDrag(v);
	}

	@Override
	public void onClick(View v) {
		DraggableRelativeLayout layout = (DraggableRelativeLayout) v;
		Intent intent = layout.getItem().getIntent();
		if (intent != null) {
			mContext.startActivity(intent);
		}
	}

	@Override
	public void onDropCompleted(View source, View target, boolean success) {
		Log.d(LAUNCHER_GRID_LOG, "Drag completed");
		if (success && source != target) {
			LauncherGridItem sourceItem = ((DraggableRelativeLayout) source).getItem();
			if (target instanceof DeleteZone) {
				if (sourceItem.canDelete()) 
					this.getItems().remove(sourceItem);
				else 
					Toast.makeText(mContext, "Can't delete this item", Toast.LENGTH_SHORT).show();
			} else {
				LauncherGridItem targetItem = ((DraggableRelativeLayout) target).getItem();
				int removeFrom = getItems().indexOf(sourceItem);
				int insertAt = getItems().size();
				if (targetItem != null) 
					insertAt = getItems().indexOf(targetItem);
				LauncherGridItem item = getItems().remove(removeFrom);
				if (insertAt >= getItems().size()) 
					insertAt = getItems().size();
				getItems().add(insertAt, item);
			}
			this.notifyDataSetInvalidated();
		}
		this.dragLayer.getDragListener().onDragEnd();
	}

	@Override
	public void itemDeleted(DragSource source) {
		// this.items.remove(((DraggableRelativeLayout) source).getItem());
		// this.notifyDataSetInvalidated();
	}

	@Override
	public void onDragStarted(View source) {
		// TODO Auto-generated method stub
	}

	public void setDragListener(DragController.DragListener listener) {
		this.dragLayer.setDragListener(listener);
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
