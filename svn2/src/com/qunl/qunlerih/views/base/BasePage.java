package com.qunl.qunlerih.views.base;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author Zhaosha
 *这个page是所有page的基类。他包括 初始化页面。与Activity进行交互，请求数据
 */
public abstract class BasePage {

	protected Context context;
	protected View contentView;

	/**
	 * @param context
	 * 初始化page。构造方法中有initview();
	 */
	public BasePage(Context context) {
		this.context = context;
		contentView = initView(LayoutInflater.from(context));
	}

	/**
	 * @param inflater
	 * basepage的初始化view
	 */
	public abstract View initView(LayoutInflater inflater);

	/**
	 * 
	 */
	public abstract void initData();

	public View getContentView() {
		return contentView;
	}

	public abstract void saveDataIndb();
	
	
	protected void onResume() {
		
	}

}
