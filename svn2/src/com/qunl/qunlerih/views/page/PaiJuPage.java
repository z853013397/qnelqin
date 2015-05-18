package com.qunl.qunlerih.views.page;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qunl.qunlerih.activity.KaiShePaiJuActivity;
import com.qunl.qunlerih.activity.MainActivity;
import com.qunl.qunlerih.apis.API;
import com.qunl.qunlerih.util.SharePrefUtil;
import com.qunl.qunlerih.views.base.BasePage;
import com.qunl.qunlerih.views.customview.CustomViewPager;
import com.qunl.qunlerih.views.customview.LazyViewPager.OnPageChangeListener;
import com.qunl.qunlerih.views.page.paiju.FuJinPaiJuPage;
import com.qunl.qunlerih.views.page.paiju.GuangChangPage;
import com.qunl.qunlerih.views.page.paiju.PengYouPaiJuPage;
import com.qunlerih.R;

/**
 * @author Zhaosha 这个是好友界面 这里面有：最上面是个radiogroup 下面还是个不滚动的viewpager
 */

public class PaiJuPage extends BasePage implements OnCheckedChangeListener,
		OnClickListener {
	private static final String TAG = PaiJuPage.class.getSimpleName();
	private static int count = 0;
	private CustomViewPager cvp;
	private ArrayList<BasePage> page;
	private RadioGroup rg;
	private MyAdapter adapter;
	private TextView main_tv_makepj;
	private PengYouPaiJuPage pypj;
	public PaiJuPage(Context context) {
		super(context);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.paiju_view, null);
		main_tv_makepj = (TextView) view.findViewById(R.id.include_paiju).findViewById(R.id.title_image_textview);
		main_tv_makepj.setText("开设牌局");
		cvp = (CustomViewPager) view.findViewById(R.id.paiju_cvp);
		page = new ArrayList<BasePage>();
		initDataPage();
		main_tv_makepj.setOnClickListener(this);
		if (adapter == null) {
			adapter = new MyAdapter(context, page);
		} else {
			adapter.notifyDataSetChanged();
		}
		cvp.setAdapter(adapter);
		rg = (RadioGroup) view.findViewById(R.id.main_rg_top);
		rg.check(R.id.btn_pengyoupaiju);
		rg.setOnCheckedChangeListener(this);
		return view;
	}
	private void initDataPage() {
		pypj = new PengYouPaiJuPage(context);
		page.add(pypj);
		page.add(new FuJinPaiJuPage(context));//广场牌局
		page.add(new GuangChangPage(context));//附近牌局
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.btn_pengyoupaiju:
			cvp.setCurrentItem(0, false);
			break;
		case R.id.btn_fujinpaiju:
			cvp.setCurrentItem(1, false);
			break;
		case R.id.btn_guangchang:
			cvp.setCurrentItem(2, false);
			break;
		default:
			break;
		}
		// url...
	}

	@Override
	public void onClick(View v) {
		if(pypj.getMyTableSize() < 5) {
				Intent intent = new Intent((MainActivity) context,
						KaiShePaiJuActivity.class);
				((MainActivity) context).startActivity(intent);
			} else {
				Toast.makeText(context, "你创建的牌桌满了", 0).show();;
			}
	}

	@Override
	public void initData() {

	}

	@Override
	public void saveDataIndb() {

	}

	private class MyAdapter extends PagerAdapter {
		private Context context;
		private List<BasePage> page;

		public MyAdapter(Context context, List<BasePage> page) {
			super();
			this.context = context;
			this.page = page;
		}

		@Override
		public int getCount() {
			return page.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((CustomViewPager) container).removeView(page.get(position)
					.getContentView());
			object = null;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			((CustomViewPager) container).addView(page.get(position)
					.getContentView());
			return page.get(position).getContentView();
		}

	}


	
	@Override
	public void onResume() {
		rg.check(R.id.btn_pengyoupaiju);
		pypj.onResume();
		super.onResume();
	}
}
