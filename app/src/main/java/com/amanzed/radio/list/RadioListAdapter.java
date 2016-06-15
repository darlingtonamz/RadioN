/*
package com.amanzed.radion.list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amanzed.radio.R;
import com.amanzed.radio.ScrollingActivity;
import com.amanzed.radio.volley.AppController;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;


public class RadioListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Radio> radioItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public RadioListAdapter(Activity activity, List<Radio> radioItems) {
		this.activity = activity;
		this.radioItems = radioItems;
	}

	@Override
	public int getCount() {
		return radioItems.size();
	}

	@Override
	public Object getItem(int location) {
		return radioItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String server = ScrollingActivity.server;
		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.radio_list_item, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView radiothumbNail = (NetworkImageView) convertView
				.findViewById(R.id.radio_list_thumb);
		TextView title = (TextView) convertView.findViewById(R.id.radio_list_name);

		// getting radio data for the row
		Radio m = radioItems.get(position);

		// thumbnail image
		radiothumbNail.setDefaultImageResId(R.mipmap.launcher);
		radiothumbNail.setImageUrl(server+"img/radio/"+m.getId()+".png", imageLoader);

		// title
		title.setText(m.getName());

		return convertView;
	}

}
*/
