package com.qunl.qunlerih.views.page.paiju;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.qunlerih.R;
import com.qunl.qunlerih.views.base.BasePage;

public class GuangChangPage extends BasePage {
	private MyAdapter adapter;
	public GuangChangPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.paiju_guangchang_view, null);
		TextView ed1 = (TextView) view.findViewById(R.id.guangchang_ed1);
		TextView ed2 = (TextView) view.findViewById(R.id.guangchang_ed2);
		ListView lv = (ListView) view.findViewById(R.id.square_listview);
		
		adapter = new MyAdapter(context);
		lv.setAdapter(adapter);
		return view;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveDataIndb() {
		// TODO Auto-generated method stub

	}

	
	class MyAdapter extends BaseAdapter {
		private Context context;
		private LayoutInflater inflater;
		
		public MyAdapter(Context context) {
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
			TextView tv = (TextView) convertView.findViewById(R.id.item_friend_tableinfo);
			TextView tv1 = (TextView) convertView.findViewById(R.id.item_friend_fenshuinfo);
			TextView tv2 = (TextView) convertView.findViewById(R.id.item_friend_jiuqueni);
			TextView tv3 = (TextView) convertView.findViewById(R.id.item_friend_timeinfo);
			
			switch (position) {
			case 0:
				tv.setText("我不赖账");
				tv1.setText("1分，不封顶");
				tv2.setText("三缺一");
				tv3.setText("<1分钟");
				break;
			case 1:
				tv.setText("我在线！人满开");
				tv1.setText("5分，封顶24倍");
				tv2.setText("三缺一");
				tv3.setText("<1分钟");
			default:
				break;
			}
			return convertView;
		}
		
	}
}
