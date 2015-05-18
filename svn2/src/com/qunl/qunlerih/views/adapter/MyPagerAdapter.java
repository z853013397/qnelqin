package com.qunl.qunlerih.views.adapter;

import java.util.List;

import com.qunl.qunlerih.views.base.BasePage;
import com.qunl.qunlerih.views.customview.CustomViewPager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter {

	private Context ct;
	private List<BasePage> pages;

	public MyPagerAdapter(Context ct, List<BasePage> pages) {
		super();
		this.ct = ct;
		this.pages = pages;
	}

	@Override
	public int getCount() {
		return pages.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((CustomViewPager) container).removeView(pages.get(position)
				.getContentView());
		object = null;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((CustomViewPager) container).addView(pages.get(position)
				.getContentView());
		return pages.get(position).getContentView();
	}

}
