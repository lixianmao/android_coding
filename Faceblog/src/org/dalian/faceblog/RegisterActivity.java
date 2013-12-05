package org.dalian.faceblog;

import org.dalian.faceblog_data.API;
import org.dalian.faceblog_data.TweetAPI;
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

public class RegisterActivity extends Activity {

	public static final String USER = "user";

	private EditText editNation;
	private EditText editAccount;
	private EditText editPassword;
	private Button buttonRegister;
	private EditText twicePassword;

	private int nation;
	private String password;
	private String account;
	private String password2;

	public static String REGISTER_SUCCEED = "注册成功";
	public static String REGISTER_FAILED = "注册失败";
	public static String EMPTY_USER = "帐号或密码不能为空";
	public static String ACCOUNT_EXIST = "帐号已存在";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		editNation = (EditText) this.findViewById(R.id.set_nation);
		editAccount = (EditText) this.findViewById(R.id.set_account);
		editPassword = (EditText) this.findViewById(R.id.set_password);
		buttonRegister = (Button) this.findViewById(R.id.set_register);
		twicePassword = (EditText) this.findViewById(R.id.twice_password);

		OnClickListener mClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				nation = Integer.parseInt(editNation.getText().toString());
				account = editAccount.getText().toString();
				password = editPassword.getText().toString();
				password2 = twicePassword.getText().toString();
				API api = new API();

				if (account.equals("") || password.equals("")
						|| password2.equals("")) {
					Toast.makeText(getApplicationContext(), EMPTY_USER,
							Toast.LENGTH_SHORT).show();
				} else if (api.isExist(account)) { // 判断帐号是否存在，避免文件冲突
					Toast.makeText(getApplicationContext(), ACCOUNT_EXIST,
							Toast.LENGTH_SHORT).show();
				} else {
					if (password.equals(password2)) {

						JSONObject user = api.createUser();
						api.registerUser(user, account, password, nation);
						api.save(user);
						Toast.makeText(getApplicationContext(),
								REGISTER_SUCCEED, Toast.LENGTH_SHORT).show();

						new TweetAPI().initBlog(); // 系统第一条微博
						Intent intent = new Intent();
						intent.setClass(RegisterActivity.this,
								PageInputActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(),
								REGISTER_FAILED, Toast.LENGTH_SHORT).show();
					}
				}

			}
		};
		buttonRegister.setOnClickListener(mClickListener);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
