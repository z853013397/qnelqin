/**
 * 
 */
package com.qunl.qunlerih.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qunlerih.R;

/**
 * @author Zhaosha
 *
 */
public class RegisterActivity extends Activity {
	private EditText userNameEditText;
	private EditText passwordEditText;
	private EditText confirmPwdEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		userNameEditText = (EditText) findViewById(R.id.register_name);
		passwordEditText = (EditText) findViewById(R.id.register_password);
		confirmPwdEditText = (EditText) findViewById(R.id.register_confirm_password);
	}

	/**
	 * @param v
	 * 这个是注册的按钮
	 */
	public void register(View v) {
		final String username = userNameEditText.getText().toString().trim();
		final String pwd = passwordEditText.getText().toString().trim();
		String confirm_pwd = confirmPwdEditText.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, "用户名不能为空", 0).show();
			userNameEditText.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, "密码不能为空", 0).show();
			passwordEditText.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(confirm_pwd)) {
			Toast.makeText(this, "确认密码不能为空", 0).show();
			confirmPwdEditText.requestFocus();
			return;
		}
		if (!pwd.equals(confirm_pwd)) {
			Toast.makeText(this, "二次输入密码一致", 0).show();
			passwordEditText.requestFocus();
			return;
		}
		
		
		if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)){
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage("正在注册");
			pd.show();

			new Thread(new Runnable() {
				@Override
				public void run() {
//					调用注册接口
				}
			}).start();		
		
		}
	}
}
