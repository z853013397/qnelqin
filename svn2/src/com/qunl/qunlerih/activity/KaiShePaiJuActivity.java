/**
 * 
 */
package com.qunl.qunlerih.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
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
import com.qunl.qunlerih.apis.API;
import com.qunl.qunlerih.dao.MyFriendInfoBean.TableInfo;
import com.qunl.qunlerih.util.SharePrefUtil;
import com.qunl.qunlerih.views.adapter.GridViewAdapter;
import com.qunlerih.R;
import com.qunlerih.R.style;

/**
 * @author Zhaosha 这个Activity是mainctivity开设牌局点击后的跳转。结束后要返回设置信息给mainactivity
 * @param <DialogWindows>
 *            架构除了问题 要重新来
 */
public class KaiShePaiJuActivity extends Activity implements OnClickListener {
	private static final String TAG = "KaiShePaiJu";
	private TextView tvBack; // 标题栏的返回按钮
	private TextView tvInfo;// 桌子的名字
	private TextView kaitaiInfo;// 桌子的名字
	private GridViewAdapter adapter;
	private Dialog dialog;
	private TableInfo tableInfo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pullData();
		setContentView(R.layout.activity_kaishepaiju);
		ImageView table2 = (ImageView) findViewById(R.id.table2_image);
		tvBack = (TextView) findViewById(R.id.kaishepaiju_back);
		tvInfo = (TextView) findViewById(R.id.kaishepaiju_zhuohao);
		kaitaiInfo = (TextView) findViewById(R.id.kaitai_info);
		tvBack.setOnClickListener(this);
		table2.setOnClickListener(this);
		tvInfo.setText(SharePrefUtil.getString(this, "name", null) + "开台");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.kaishepaiju_back:// 这个是返回的点击事件

			finish();
			break;
		case R.id.table2_image:// 这个是第二个图片的点击事件 弹出dialog
			showDiaLog();
			break;

		case R.id.adress_invite:// 这个是邀请好友的点击事件
			adressInvite();
			break;
		default:
			break;
		}
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

	/**
	 * 这个是创建牌局的网络请求
	 * 
	 * @param text
	 */
	private void pullData() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("tname",
				SharePrefUtil.getString(this, "name", null) + "开台");
		params.addBodyParameter("uid",
				SharePrefUtil.getString(this, "uid", null));
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		http.send(HttpRequest.HttpMethod.POST, API.createTable, params,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Log.i("createTable", "onFailure");
					}

					@Override
					public void onSuccess(ResponseInfo<String> response) {
						Log.i("createTable", "success");
						Toast.makeText(KaiShePaiJuActivity.this, "创建牌局成功", 0)
								.show();
						String str = response.result;
						Log.i("创建牌局", str);
						Gson gson = new Gson();
						tableInfo = gson.fromJson(str, TableInfo.class);
						kaitaiInfo.setText("桌号:" + tableInfo.tid);
						SharePrefUtil.saveString(KaiShePaiJuActivity.this,
								"tid", tableInfo.tid);
					}
				});
	}
	/**
	 * 显示dialog
	 */
	private void showDiaLog() {
		dialog = new Dialog(this, style.custom_dialog_theme);
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

	/**
	 * @param dialog
	 *            找到dialog里面的控件并初始化
	 */
	private void initDialog(Dialog dialog) {
		ImageView adressInvite = (ImageView) dialog
				.findViewById(R.id.adress_invite);
		GridView gridView = (GridView) dialog
				.findViewById(R.id.dialog_gridview);
		adressInvite.setOnClickListener(this);
		adapter = new GridViewAdapter(this);
		gridView.setAdapter(adapter);
	}

}
