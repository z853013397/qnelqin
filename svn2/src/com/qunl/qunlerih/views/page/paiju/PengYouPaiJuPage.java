package com.qunl.qunlerih.views.page.paiju;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qunl.qunlerih.activity.KaiShePaiJuActivity;
import com.qunl.qunlerih.activity.MainActivity;
import com.qunl.qunlerih.activity.PlayGames;
import com.qunl.qunlerih.apis.API;
import com.qunl.qunlerih.dao.MyFriendInfoBean;
import com.qunl.qunlerih.dao.MyFriendInfoBean.TableInfo;
import com.qunl.qunlerih.util.SharePrefUtil;
import com.qunl.qunlerih.views.adapter.SearchTableAdapter;
import com.qunl.qunlerih.views.adapter.MyTableAdapter;
import com.qunl.qunlerih.views.base.BasePage;
import com.qunlerih.R;

public class PengYouPaiJuPage extends BasePage implements OnClickListener,
		OnItemClickListener {
	//
	private SearchTableAdapter searchTableAdapter;// 搜索的adatper
	private MyTableAdapter myTableAdapter;// 我的牌局信息

	private ListView tableListview;// 最上层的listview
	private ListView searchListView;// 下层的listview

	private List<TableInfo> myTableList;// 这个装着我的桌子信息
	private List<TableInfo> searchTableList;// 这个装着搜索的信息

	private EditText edtext;// 搜索的edtext

	public PengYouPaiJuPage(Context context) {
		super(context);
		initListview();
	}

	private void initListview() {
		addFootView();
		myTableAdapter = new MyTableAdapter(context, myTableList);
		tableListview.setAdapter(myTableAdapter);
		initData();
		if (SharePrefUtil.getString(context, "data", null) != null) {
			SharePrefUtil.saveString(context, "data", null);
			searchTable(edtext.getText().toString());
		}
	}

	private void addFootView() {
		View view = LayoutInflater.from(context).inflate(R.layout.scroll_view,
				null);
		searchListView = (ListView) view.findViewById(R.id.listView2);
		searchListView.setOnItemClickListener(this);
		tableListview.addFooterView(view);
	}

	public int getMyTableSize() {
		return myTableList.size();
	}

	@Override
	public View initView(LayoutInflater inflater) {
		searchTableList = new ArrayList<TableInfo>();
		myTableList = new ArrayList<TableInfo>();
		View view = inflater.inflate(R.layout.paiju_pengyoupaiju_view, null);
		tableListview = (ListView) view
				.findViewById(R.id.pengyoupaiju_listview);
		tableListview.setOnItemClickListener(this);
		edtext = (EditText) view.findViewById(R.id.pengyoupaiju_edittext);

		Button bunSearch = (Button) view.findViewById(R.id.choose);
		Button bunRefresh = (Button) view.findViewById(R.id.refresh);
		bunSearch.setOnClickListener(this);
		bunRefresh.setOnClickListener(this);
		return view;
	}

	/**
	 * 这个是搜索的网络访问
	 * 
	 * @param str
	 */
	public void searchTable(String str) {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		http.send(HttpRequest.HttpMethod.GET, API.createTable + "0/" + str,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException httpException,
							String str) {
						Toast.makeText(context, "搜索失败", 0).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> info) {
						if (info == null)
							return;
						String json = info.result;
						Log.i("这个是搜索的信息", json);
						Gson gson = new Gson();
						MyFriendInfoBean fromJson = gson.fromJson(info.result,
								MyFriendInfoBean.class);
						Log.i("这个是搜索的信息", (fromJson == null) + "");
						if (fromJson.record != null) {
							Log.i("这个是搜索的信息", fromJson.result);
							searchTableList.clear();
							searchTableList.addAll(fromJson.record);
							Log.i("id null", searchTableList.size() + "");
							if (searchTableAdapter == null) {
								searchTableAdapter = new SearchTableAdapter(
										context, searchTableList);
								searchListView.setAdapter(searchTableAdapter);

							} else {
								searchTableAdapter.notifyDataSetChanged();
							}
						} else {
							Toast.makeText(context, "没有这个桌子", 0).show();
						}
					}
				});
	}

	@Override
	public void initData() {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		String uid = SharePrefUtil.getString(context, "uid", null);
		android.util.Log.i("初始话自己创建的牌局的uid", uid);
		http.send(HttpMethod.GET, API.attendTable + "/" + uid + "/0",
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException exception, String str) {
						Toast.makeText(context, "获取失败", 0).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> response) {
						Log.i("这个是自己id的搜索", response.result);
						Gson gson = new Gson();
						MyFriendInfoBean fromJson = gson.fromJson(
								response.result, MyFriendInfoBean.class);
						if (fromJson.record != null) {
							myTableList.clear();
							myTableList.addAll(fromJson.record);
							Log.i("size", myTableList.size() + "");
							myTableAdapter.notifyDataSetChanged();
						}
					}
				});
	}

	@Override
	public void saveDataIndb() {

	}

	@Override
	public void onResume() {
		initData();
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.choose:
			String string = edtext.getText().toString();
			searchTable(string);
			break;
		case R.id.refresh:
			initData();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.pengyoupaiju_listview:// 这个是s面listview
			android.util.Log.i("position", position + "");
			TableInfo MyTableitem = (TableInfo) myTableAdapter
					.getItem(position);
			Bundle bundle = new Bundle();
			bundle.putSerializable("AttendTable", MyTableitem);
			Intent intent = new Intent(context, PlayGames.class);
			intent.putExtras(bundle);
			context.startActivity(intent);
			break;
		case R.id.listView2:// 这个是下面listview
			TableInfo Searchitem = (TableInfo) searchTableAdapter
					.getItem(position);
			joinGame(Searchitem);
			break;
		}
	}

	/**
	 * 加入牌局的网络访问
	 * 
	 * @param record
	 */
	private void joinGame(TableInfo record) {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		android.util.Log.i("record", record + "");
		RequestParams params = new RequestParams();
		params.addBodyParameter("uid",
				SharePrefUtil.getString(context, "uid", null));
		params.addBodyParameter("tid", record.tid);
		android.util.Log.i("tid", record.tid);
		android.util.Log
				.i("uid", SharePrefUtil.getString(context, "uid", null));
		params.addBodyParameter("type", "0");
		http.send(HttpRequest.HttpMethod.POST, API.attendTable, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						android.util.Log.i("请求失败", "onFailure");
					}

					@Override
					public void onSuccess(ResponseInfo<String> info) {
						Gson gson = new Gson();
						TableInfo attendTable = gson.fromJson(info.result,
								TableInfo.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("AttendTable", attendTable);
						Intent intent = new Intent(context, PlayGames.class);
						intent.putExtras(bundle);
						context.startActivity(intent);
					}
				});
	}
}
