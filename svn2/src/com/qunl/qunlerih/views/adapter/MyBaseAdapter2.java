package com.qunl.qunlerih.views.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qunlerih.R;
import com.qunl.qunlerih.views.base.BasePage;

public class MyBaseAdapter2 extends BaseAdapter {
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

	public MyBaseAdapter2(Context context) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
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
		TextView tv1 = (TextView) convertView.findViewById(R.id.item_friend_tableinfo);//��ֻ�ǲ����õ�
		TextView tv2 = (TextView) convertView.findViewById(R.id.item_friend_timeinfo);//
		TextView tv3 = (TextView) convertView.findViewById(R.id.item_friend_jiuqueni);//
		TextView tv4 = (TextView) convertView.findViewById(R.id.item_friend_location);//
		TextView tv5 = (TextView) convertView.findViewById(R.id.item_friend_fenshuinfo);//
		
		switch (position) {
		case 0:
			tv1.setText("我不赖账");
			tv2.setText("<3小时");
			tv3.setText("三缺一");
			tv4.setText("1000米以内");
			break;
		case 1:
			tv1.setText("我在线！人满开");
			tv2.setText("<2小时");
			tv3.setText("房主离线");
			tv4.setText("1000米以内");
			tv5.setText("5分，封顶24倍");
			break;

		default:
			break;
		}
		return convertView;
	}

}
