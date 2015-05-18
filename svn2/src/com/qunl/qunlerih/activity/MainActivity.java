package com.qunl.qunlerih.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.qunlerih.R;
import com.qunl.qunlerih.views.adapter.MyPagerAdapter;
import com.qunl.qunlerih.views.base.BasePage;
import com.qunl.qunlerih.views.customview.CustomViewPager;
import com.qunl.qunlerih.views.page.HaoYouPage;
import com.qunl.qunlerih.views.page.PaiJuPage;
import com.qunl.qunlerih.views.page.WoPage;
import com.qunl.qunlerih.views.page.ZhangBenPage;

/**
 * @author Zhaosha 界面底下是个radiogroup 上面是个自定义的viewpager。viewpager里面装着basepager
 */
public class MainActivity extends Activity implements OnCheckedChangeListener {
	private CustomViewPager cvp;
	private MyPagerAdapter myPagerAdapter;
	private ArrayList<BasePage> page = new ArrayList<BasePage>();
	private RadioGroup rg;
	private int checkedID;
	private PaiJuPage paiju;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		cvp = (CustomViewPager) findViewById(R.id.main_cvp);
		rg = (RadioGroup) findViewById(R.id.main_rg_bottom);
		rg.check(R.id.main_bottom_btn_paiju);
		rg.setOnCheckedChangeListener(this);
		initPage();

		myPagerAdapter = new MyPagerAdapter(this, page);
		cvp.setAdapter(myPagerAdapter);

	}

	/**
	 * 初始化arraylist -page
	 */
	private void initPage() {
		paiju = new PaiJuPage(this);// new 这个代码的目的是为了回调
		page.add(paiju);// 这个page里面装的是牌局
		page.add(new ZhangBenPage(this));// 这个是账本
		page.add(new HaoYouPage(this));// 这个是好友
		page.add(new WoPage(this));// 这个是我
	}

	/*
	 * (non-Javadoc) 这个是底下Activity的radiogroup的点击事件。现在没有初始化数据。后面必须加数据初始化
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.main_bottom_btn_paiju:
			cvp.setCurrentItem(0, false);
			break;
		case R.id.main_bottom_btn_zhangben:
			cvp.setCurrentItem(1, false);
			break;
		case R.id.main_bottom_btn_haoyou:
			cvp.setCurrentItem(2, false);
			break;
		case R.id.main_bottom_btn_wo:
			cvp.setCurrentItem(3, false);
			break;
		default:
			break;
		}
		//
	}

	/*
	 * 
	 * 开设牌局返回来的数据，在这儿接收
	 */
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == 20) {
//			
//			Bundle mBundle = data.getExtras();
//			MyTableBean myTable = (MyTableBean) mBundle
//					.getSerializable("kaishepaiju");
////			paiju.updateListPage(myTable);
//			data = null;// 释放资源
//		}
//	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		Log.i("MainActivity", "onResume");
		rg.check(R.id.main_bottom_btn_paiju);
		paiju.onResume();
		super.onResume();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View view = getCurrentFocus();
			if (isHideInput(view, ev)) {
				HideSoftInput(view.getWindowToken());
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	// 判定是否需要隐藏
	private boolean isHideInput(View v, MotionEvent ev) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
					+ v.getWidth();
			if (ev.getX() > left && ev.getX() < right && ev.getY() > top
					&& ev.getY() < bottom) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	// 隐藏软键盘
	private void HideSoftInput(IBinder token) {
		if (token != null) {
			InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(token,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
