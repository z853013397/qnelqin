package com.qunl.qunlerih.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qunl.qunlerih.apis.API;
import com.qunl.qunlerih.dao.MyFriendInfoBean;
import com.qunl.qunlerih.dao.MyFriendInfoBean.TableInfo;
import com.qunl.qunlerih.util.SharePrefUtil;
import com.qunl.qunlerih.views.adapter.GridViewAdapter;
import com.qunlerih.R;

public class PlayGames extends Activity implements OnClickListener {
	private TableInfo attentTable;
	private ImageView table1;
	private ImageView table2;
	private ImageView table3;
	private TextView tableID;
	private TextView startGame;
	private TextView table2Name;
	private TextView table3Name;
	private TextView PaijuName;
	private Timer mTimer;
	private String tid;// 这个是所有用户的id
	private TextView backBtn;
	private String ownerid;
	private boolean isOwner = false;
	private Dialog dialog;
	private GridViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kaishepaiju);
		initView();
		getTableInfo();
		ownerid = attentTable.ownerid;
		if (ownerid.equals(SharePrefUtil.getString(this, "uid", null))) {
			isOwner = true;
		} else {
			isOwner = false;
		}
		updateUI(attentTable);
		setTimerTask();
	}

	private void updateUI(TableInfo attentTable) {
		tid = attentTable.tid;
		tableID.setText("桌号:" + tid);
		PaijuName.setText(attentTable.tname);
		String members = attentTable.uid;
		String[] member = members.split(",");

		showPhoto(member);
	}

	private void showPhoto(String[] member) {
		Log.i("标记", "true");
		Log.i("member", member.length + "");

		switch (member.length) {
		case 1:
			setBackgroudPhoto(table1);
			setBackgroudDefault(table2);
			setBackgroudDefault(table3);
			table2Name.setVisibility(View.GONE);
			table3Name.setVisibility(View.GONE);
			table2.setEnabled(true);
			table3.setEnabled(true);
			startGame.setVisibility(View.GONE);
			break;
		case 2:
			setBackgroudPhoto(table1);
			setBackgroudPhoto(table2);
			setBackgroudDefault(table3);
			table2.setEnabled(false);
			table3.setEnabled(true);
			table2Name.setVisibility(View.VISIBLE);
			table3Name.setVisibility(View.GONE);
			startGame.setVisibility(View.GONE);
			break;
		case 3:
			table2.setEnabled(false);
			table3.setEnabled(false);
			setBackgroudPhoto(table1);
			setBackgroudPhoto(table2);
			setBackgroudPhoto(table3);
			table2Name.setVisibility(View.VISIBLE);
			table3Name.setVisibility(View.VISIBLE);
			if(isOwner) {
			startGame.setVisibility(View.VISIBLE);
			startGame.setOnClickListener(this);
			}
			break;
		default:
			break;
		}
	}

	private void getTableInfo() {
		Bundle bundle = getIntent().getExtras();
		attentTable = (TableInfo) bundle.getSerializable("AttendTable");
	}

	private void setBackgroudDefault(ImageView table32) {
		table32.setBackgroundResource(R.drawable.picture_cha);
	}

	private void setBackgroudPhoto(ImageView table12) {
		table12.setBackgroundResource(R.drawable.photo_wdzz1);
	}

	private void initView() {
		mTimer = new Timer();
		backBtn = (TextView) findViewById(R.id.kaishepaiju_back);
		backBtn.setOnClickListener(this);
		table1 = (ImageView) findViewById(R.id.table1_image);
		table2 = (ImageView) findViewById(R.id.table2_image);
		table2.setOnClickListener(this);
		table3 = (ImageView) findViewById(R.id.table3_image);
		table3.setOnClickListener(this);
		startGame = (TextView) findViewById(R.id.start_game);
		table2Name = (TextView) findViewById(R.id.table2_name);
		table3Name = (TextView) findViewById(R.id.table3_name);
		tableID = (TextView) findViewById(R.id.kaitai_info);
		PaijuName = (TextView) findViewById(R.id.kaishepaiju_zhuohao);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.kaishepaiju_back:
			if (isOwner) {
				finish();
			} else {
				exitPaiju();
				finish();
			}
			break;
		case R.id.start_game:
			Toast.makeText(this, "开始游戏", 0).show();
			break;
			
		case R.id.adress_invite:// 这个是邀请好友的点击事件
			adressInvite();
			break;
		case R.id.table2_image:// 这个是第二个图片的点击事件 弹出dialog
		case R.id.table3_image:// 这个是第二个图片的点击事件 弹出dialog
			if(isOwner)
			showDialog();
			break;
		}
	}

	private void exitPaiju() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("tid", attentTable.tid);
		Log.i("tid", attentTable.tid);
		params.addBodyParameter("uid",
				SharePrefUtil.getString(this, "uid", null));
		Log.i("uid", SharePrefUtil.getString(this, "uid", null));
		params.addBodyParameter("type", "1");
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		http.send(HttpMethod.POST, API.attendTable, params, null);
	}

	/**
	 * 
	 * 
	 */
	private void askNetUpdate() {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		http.send(HttpRequest.HttpMethod.GET, API.attendTable + "0/" + tid,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Log.i("有数据么", false + "");
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Log.i("有数据么", arg0.result);
						Gson gson = new Gson();
						MyFriendInfoBean fromJson = gson.fromJson(arg0.result,
								MyFriendInfoBean.class);
						TableInfo tableInfo = fromJson.record.get(0);
						updateUI(tableInfo);
					}
				});
	}

	private void setTimerTask() {
		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				askNetUpdate();
			}
		}, 2000, 2000);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (isOwner) {

			Log.i("执行了么", isOwner + "");
			finish();
		} else {
			Log.i("执行了么", isOwner + "");
			exitPaiju();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		mTimer.cancel();
		super.onDestroy();
	}

	private void showDialog() {
		dialog = new Dialog(this, R.style.custom_dialog_theme);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.kaishepaiju_dialog);
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		window.setWindowAnimations(R.style.dialog_animation);
		initDialog(dialog);
		dialog.show();
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		dialog.getWindow().setAttributes(lp);
	}

	private void initDialog(Dialog dialog) {
		ImageView adressInvite = (ImageView) dialog
				.findViewById(R.id.adress_invite);
		GridView gridView = (GridView) dialog
				.findViewById(R.id.dialog_gridview);
		adressInvite.setOnClickListener(this);
		adapter = new GridViewAdapter(this);
		gridView.setAdapter(adapter);
	}
	
	
	/**
	 * 弹出邀请好友
	 */
	private void adressInvite() {
		Log.i("adress_invite", "邀请好友");// 此处是邀请好友
		Intent intent = new Intent(this, ContactsActivity.class);
		startActivity(intent);
		dialog.dismiss();

	}
}
