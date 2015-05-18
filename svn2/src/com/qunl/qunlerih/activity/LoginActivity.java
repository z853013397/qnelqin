/**
 * 
 */
package com.qunl.qunlerih.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.qunl.qunlerih.apis.API;
import com.qunl.qunlerih.dao.UserInfo;
import com.qunl.qunlerih.util.SharePrefUtil;
import com.qunlerih.R;

/**
 * @author Zhaosha
 *
 */
public class LoginActivity extends Activity implements OnClickListener {

	private EditText userNameEditText;
	private EditText passwordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();

	}

	private void initView() {
		userNameEditText = (EditText) findViewById(R.id.nickname);
		passwordEditText = (EditText) findViewById(R.id.password);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_log_in:
			login();
			break;
		case R.id.register:
			register();
			break;
		}
	}

	private void register() {
		startActivity(new Intent(this,RegisterActivity.class));
	}

	private void login() {
		String name = userNameEditText.getText().toString().trim();
		String password = passwordEditText.getText().toString().trim();

			commitData(name,password); // 再提交到服务器
	}

	private void commitData(final String name,final String password) {
		HttpUtils http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(0);
		http.send(HttpRequest.HttpMethod.GET, API.checkLogin+name,
				new RequestCallBack<String>() {
					@Override
					public void onFailure(
							com.lidroid.xutils.exception.HttpException arg0,
							String arg1) {
						Toast.makeText(LoginActivity.this, "onFailure", 0)
								.show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Toast.makeText(LoginActivity.this, "onSuccess", 0)
								.show();
						String string = responseInfo.result;
						Log.i("responseInfo.result= ", string);
						Gson gson = new Gson();
						UserInfo userInfo = gson.fromJson(string, UserInfo.class);
						Log.i("json", string);
						Log.i("user.uid", userInfo.uid);
						saveUserInfo(userInfo);//保存用户登录信息
						startMainActivity();
					}

					private void saveUserInfo(UserInfo userInfo) {
						SharePrefUtil.saveString(LoginActivity.this, "uid", userInfo.uid);//这个是用户id
						SharePrefUtil.saveString(LoginActivity.this, "name",name);//用户的输入名字
					}
				});

	}

	private void startMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		intent = null;
		finish();
	}
}
