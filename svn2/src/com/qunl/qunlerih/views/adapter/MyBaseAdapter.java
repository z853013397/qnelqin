package com.qunl.qunlerih.views.adapter;

import java.util.List;


import com.qunl.qunlerih.views.base.BasePage;
import com.qunlerih.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyBaseAdapter extends BaseAdapter {
	private Context context;
	private List<BasePage> page;
	private LayoutInflater inflater;
	
//	
//	public MyBaseAdapter(Context context, List<BasePage> page) {
//		super();
//		this.context = context;
//		this.page = page;
//		inflater = LayoutInflater.from(context);
//	}

	public MyBaseAdapter(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.item_paiju, null);
		return convertView;
	}

}
