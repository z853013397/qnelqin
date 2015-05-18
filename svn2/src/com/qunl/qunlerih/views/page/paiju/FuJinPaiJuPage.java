package com.qunl.qunlerih.views.page.paiju;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.qunlerih.R;
import com.qunl.qunlerih.views.adapter.MyTableAdapter;
import com.qunl.qunlerih.views.adapter.MyBaseAdapter2;
import com.qunl.qunlerih.views.base.BasePage;

public class FuJinPaiJuPage extends BasePage {
	private ListView mListView;
	private MyBaseAdapter2 adapter;
	public FuJinPaiJuPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.just_listview, null);
		mListView = (ListView) view.findViewById(R.id.just_listview);
		adapter = new MyBaseAdapter2(context);
		mListView.setAdapter(adapter);
		return view;
	}

	@Override
	public void initData() {

	}

	@Override
	public void saveDataIndb() {

	}

}
