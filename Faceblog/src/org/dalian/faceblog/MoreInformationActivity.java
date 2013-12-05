package org.dalian.faceblog;

import org.dalian.faceblog_data.API;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MoreInformationActivity extends Activity {

	private Button editButton;
	private TextView loginName;
	private TextView nickname;
	private TextView gender;
	private TextView nation;
	private TextView age;
	private TextView introdution;
	private TextView mailbox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_information);

		loginName = (TextView) findViewById(R.id.tv_login_name);
		nickname = (TextView) findViewById(R.id.tv_nickname);
		gender = (TextView) findViewById(R.id.tv_gender);
		nation = (TextView) findViewById(R.id.tv_nation);
		age = (TextView) findViewById(R.id.tv_age);
		introdution = (TextView) findViewById(R.id.tv_introduction);
		mailbox = (TextView) findViewById(R.id.tv_mailbox);

		JSONObject user = new API().load();
		try {
			loginName.setText(user.getString("account"));
			nickname.setText(user.getString("nickname"));
			gender.setText(gender(user.getInt("gender")));
			nation.setText(nation(user.getInt("nation")));
			if (user.getInt("nation") == 0) {
				age.setText(user.getInt("age") + 1 + "");
			}
			else {
				age.setText(user.getInt("age") + "");
			}
			introdution.setText(user.getString("introduction"));
			mailbox.setText(user.getString("mailbox"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		editButton = (Button) this.findViewById(R.id.btn_edit_info);
		editButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MoreInformationActivity.this,
						PageInputActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more_information, menu);
		return true;
	}
	
	/*根据数字显示国家*/
	public String nation(int id) {
		if(id == 0) 
			return "中国";
		else if(id == 1){
			return "美国";
		}
		else
			return "其他国家";
	}
	/*根据id显示性别*/
	public String gender(int id) {
		if(id == 0) return "男";
		else return "女";
	}

}
