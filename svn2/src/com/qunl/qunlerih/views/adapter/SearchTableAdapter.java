package com.qunl.qunlerih.views.adapter;

import java.util.List;

import com.qunl.qunlerih.dao.MyFriendInfoBean.TableInfo;
import com.qunlerih.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchTableAdapter extends BaseAdapter {
	private Context context;
	private List<TableInfo> tableInfo;

	private LayoutInflater inflater;

	public SearchTableAdapter(Context context, List<TableInfo> tableInfo) {
		this.context = context;
		this.tableInfo = tableInfo;
		
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		Log.i("tableInfo1", tableInfo.size()+"");
		return tableInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return tableInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i("go to ?", "gotoddddddddddd");		
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_paiju, null);
			holder.tableName = (TextView) convertView
					.findViewById(R.id.item_friend_tableinfo);			
			Log.i("go to ?", "goto");		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tableName.setText(tableInfo.get(position).tname);
		
		
		return convertView;
	}

	class ViewHolder {
		TextView tableName;
	}

}
