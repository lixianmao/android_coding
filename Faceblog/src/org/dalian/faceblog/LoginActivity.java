package org.dalian.faceblog;

import org.dalian.faceblog_data.API;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText editAccount;
	private EditText editPassword;
	private Button buttonLogin;
	private Button buttonRegister;
	private Button buttonMorePop;

	private String account;
	private String password;

	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String LOGIN_FAILED = "帐号或密码有误";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editAccount = (EditText) findViewById(R.id.et_account);
		editPassword = (EditText) findViewById(R.id.et_password);
		buttonLogin = (Button) findViewById(R.id.btn_login);
		buttonRegister = (Button) findViewById(R.id.btn_register);
		buttonMorePop = (Button) findViewById(R.id.btn_more_pop);

		buttonLogin.setOnClickListener(mOnClickListener);
		buttonRegister.setOnClickListener(mOnClickListener);
		buttonMorePop.setOnClickListener(mOnClickListener);

	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_login:
				account = editAccount.getText().toString();
				password = editPassword.getText().toString();
				API api = new API();
				if (api.accountExist(account)) {
					JSONObject user = api.findAccount(account, password);
					if (user != null) {
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this,
								PageShowActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), LOGIN_FAILED,
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), LOGIN_FAILED,
							Toast.LENGTH_SHORT).show();
				}
				JSONObject user = api.findAccount(account, password);
				if (user != null) {
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, PageShowActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), LOGIN_FAILED,
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btn_register:
				Intent intent2 = new Intent();
				intent2.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent2);
			case R.id.btn_more_pop:
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
