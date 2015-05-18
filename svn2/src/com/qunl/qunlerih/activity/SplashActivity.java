package com.qunl.qunlerih.activity;

import com.qunl.qunlerih.util.SharePrefUtil;
import com.qunlerih.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				checkPassword();

			}
		}, 1000);
		Uri uridata = this.getIntent().getData();
		if (uridata != null) {
			String mydata = uridata.getQueryParameter("data");
			SharePrefUtil.saveString(this, "data", mydata);
			Log.v("test", mydata);
		}
	}

	private void checkPassword() {

		String name = SharePrefUtil.getString(this, "name", null);
		if (name != null) {// 这儿得到的是用户名
			mainIn();
		} else {
			login();
		}
		finish();
	}

	/**
	 * 这个是进入主界面.这儿要进行联网判断
	 */
	private void mainIn() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	/**
	 * 这个是进入登录界面
	 */
	private void login() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
}