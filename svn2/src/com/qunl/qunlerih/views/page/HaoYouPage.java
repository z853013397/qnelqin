package com.qunl.qunlerih.views.page;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.qunlerih.R;
import com.qunl.qunlerih.views.adapter.MyPagerAdapter;
import com.qunl.qunlerih.views.base.BasePage;
import com.qunl.qunlerih.views.customview.CustomViewPager;
import com.qunl.qunlerih.views.page.myfriend.Haoyou2Page;
import com.qunl.qunlerih.views.page.myfriend.PaiYouPage;

/**
 * @author Zhaosha
 * 这个是好友界面 这里面有：最上面是个radiogroup 下面是个不会滑动的viewpager viewpager里面装着listview
 */
public class HaoYouPage extends BasePage implements OnCheckedChangeListener {

	private RadioGroup rg;
	private CustomViewPager cvp;
	private List<BasePage> list;

	private MyPagerAdapter adapter;

	public HaoYouPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		list = new ArrayList<BasePage>();
		View view = inflater.inflate(R.layout.myfriend_view, null);
		initViewBase(view);
		initList();
		adapter = new MyPagerAdapter(context, list);
		cvp.setAdapter(adapter);
		rg.check(R.id.myfriend_view_paiyou);
		rg.setOnCheckedChangeListener(this);
		return view;
	}

	private void initList() {
		list.add(new PaiYouPage(context));
		list.add(new Haoyou2Page(context));
	}

	private void initViewBase(View view) {
		rg = (RadioGroup) view.findViewById(R.id.myfriend_rg);
		cvp = (CustomViewPager) view.findViewById(R.id.myfriend_cvp);
	}

	@Override
	public void initData() {

	}

	@Override
	public void saveDataIndb() {

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.myfriend_view_paiyou:
			cvp.setCurrentItem(0, false);
			break;
		case R.id.myfriend_view_haoyou:
			cvp.setCurrentItem(1, false);
			break;
		default:
			break;
		}
	}
}
