package com.qunl.qunlerih.views.adapter;

import java.util.List;

import com.qunl.qunlerih.dao.MyFriendInfoBean.TableInfo;
import com.qunlerih.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Zhaosha 这个要改
 */
public class MyTableAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private List<TableInfo> tableInfo;

	public MyTableAdapter(Context context, List<TableInfo> tableInfo) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.tableInfo = tableInfo;
	}

	@Override
	public int getCount() {
		return tableInfo.size(); // 有创建牌局，没有好友牌局的显示
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
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
		convertView = inflater.inflate(R.layout.header_paiju_pengyoupaiju, null);
		holder.tableName = (TextView) convertView.findViewById(R.id.main_tv_tableinfo);
		
		
		convertView.setTag(holder);
		
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tableName.setText(tableInfo.get(position).tname);
		
		return convertView;
	}
	
	class ViewHolder {
		TextView tableName;
	}

}