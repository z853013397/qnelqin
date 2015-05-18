package com.qunl.qunlerih.views.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.qunlerih.R;

import com.qunl.qunlerih.views.base.BasePage;
import com.qunlerih.R;

/**
 * @author Zhaosha
 *这个是radiogroup底下我的界面。这个界面要重做。做成listview
 */
public class WoPage extends BasePage {

	public WoPage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.wo_view, null);
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

}
