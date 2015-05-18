package com.qunl.qunlerih.views.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.qunlerih.R;
import com.qunl.qunlerih.views.base.BasePage;

/**
 * @author Zhaosha
 *这个是账本上面是很多的textview 其中有个小图标能点击弹出时间信息。
 *下面是个listview里面是每日输赢的明细
 *明细和弹出的时间信息进行联动
 */
public class ZhangBenPage extends BasePage {
	private MyAdapter adapter;
	public ZhangBenPage(Context context) {
		super(context);
		
		
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.zhangben_view, null);
		ListView mListView = (ListView) view.findViewById(R.id.zhangben_listview);
		adapter = new MyAdapter(context);
		mListView.setAdapter(adapter);
		
		return view;
	}

	@Override
	public void initData() {

	}

	@Override
	public void saveDataIndb() {

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
			return 4;
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
			convertView = inflater.inflate(R.layout.item_zhangben, null);
			
			return convertView;
		}
		
	}
}
