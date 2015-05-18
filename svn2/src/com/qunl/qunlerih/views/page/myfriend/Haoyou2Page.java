package com.qunl.qunlerih.views.page.myfriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qunl.qunlerih.views.base.BasePage;

/**
 *请参照牌友界面
 */
public class Haoyou2Page extends BasePage {

	public Haoyou2Page(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		TextView v = new TextView(context);
		v.setText("这个是好友界面");
		return v;
	}

	@Override
	public void initData() {

	}

	@Override
	public void saveDataIndb() {

	}

}
