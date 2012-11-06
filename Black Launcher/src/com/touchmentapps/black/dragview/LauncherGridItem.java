package com.touchmentapps.black.dragview;

import java.net.URISyntaxException;

import org.codehaus.jackson.annotate.JsonIgnore;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class LauncherGridItem {

	private String caption;
	private Drawable imageUrl = null;
	// private Intent intent;
	private boolean deletable;
	private String intentUrl;

	public LauncherGridItem() {
	}

	public LauncherGridItem(Drawable imageUrl, String caption) {
		this.imageUrl = imageUrl;
		this.caption = caption;
	}

	public LauncherGridItem(Drawable imageUrl, String caption, Intent intent) {
		this.imageUrl = imageUrl;
		this.caption = caption;
		this.setIntentUrl(intent.toUri(Intent.URI_INTENT_SCHEME).toString());
		this.setDeletable(false);
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Drawable getImageDrawable() {
		return imageUrl;
	}

	@JsonIgnore
	public Intent getIntent() {
		try {
			return Intent.parseUri(getIntentUrl(), Intent.URI_INTENT_SCHEME);
		} catch (URISyntaxException e) {
			return new Intent();
		}
	}

	@JsonIgnore
	public void setIntent(Intent intent) {
		this.setIntentUrl(intent.toUri(Intent.URI_INTENT_SCHEME));
	}

	public void canDelete(boolean canDelete) {
		this.setDeletable(canDelete);
	}

	public boolean canDelete() {
		return this.isDeletable();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof LauncherGridItem) {
			LauncherGridItem other = (LauncherGridItem) o;
			if (this.intentUrl == null || other.intentUrl == null) {
				return super.equals(o);
			}
			try {
				Intent a = Intent.parseUri(this.intentUrl,
						Intent.URI_INTENT_SCHEME);

				Intent b = Intent.parseUri(other.intentUrl,
						Intent.URI_INTENT_SCHEME);
				if (a.filterEquals(b)) {
					if (a.getExtras() != null && b.getExtras() != null) {
						for (String key : a.getExtras().keySet()) {
							if (!b.getExtras().containsKey(key)) {
								return false;
							} else if (!a.getExtras().get(key)
									.equals(b.getExtras().get(key))) {
								return false;

							}
						}
					}
					return true;
				}
			} catch (URISyntaxException e) {
				return false;
			}
		}

		return false;
	}

	public String getIntentUrl() {
		return intentUrl;
	}

	public void setIntentUrl(String intentUrl) {
		this.intentUrl = intentUrl;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
}