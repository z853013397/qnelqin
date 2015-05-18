package com.qunl.qunlerih.views.page.myfriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.qunlerih.R;
import com.qunl.qunlerih.views.base.BasePage;

/**
 * @author Zhaosha
 *	这个是牌友界面 是个listview 里面要响应点击事件进行聊天
 */
public class PaiYouPage extends BasePage {
	private MyAdapter adapter;
	private ListView listView;

	public PaiYouPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		listView = (ListView) inflater.inflate(R.layout.just_listview, null);
		adapter = new MyAdapter(context);
		listView.setAdapter(adapter);
		
		return listView;
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
			return 8;
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
			convertView = inflater.inflate(R.layout.item_haoyou, null);
			return convertView;
		}
		
	}

}
