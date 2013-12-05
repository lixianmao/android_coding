package org.dalian.faceblog;

import org.dalian.faceblog_data.API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MoreActivity extends Activity {

	private Button manageAccount;
	private Button searchFriend;
	private EditText friendAccount;

	public static final String EMPTY_ACCOUNT = "用户不存在";
	public static final String SEARCH_SUCCESS = "添加成功";
	private String account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);

		manageAccount = (Button) findViewById(R.id.manage_account);
		searchFriend = (Button) findViewById(R.id.search_friend);
		friendAccount = (EditText) findViewById(R.id.friend_account);

		manageAccount.setOnClickListener(mOnClickListener);
		searchFriend.setOnClickListener(mOnClickListener);

	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			API api = new API();
			switch (v.getId()) {
			case R.id.search_friend:
				account = friendAccount.getText().toString();
				if (!account.equals("") && api.isExist(account)) {
					Log.v("account_exist", "查找成功");
					Toast.makeText(getApplicationContext(), SEARCH_SUCCESS,
							Toast.LENGTH_SHORT);
					JSONObject user = api.load();
					JSONObject friend = api.getUser(account);
					try {
						JSONArray focus = user.getJSONArray("focus");
						JSONArray fans = friend.getJSONArray("fans");

						focus.put(account);
						fans.put(user.getString("account"));

						user.put("focus", focus);
						friend.put("fans", fans);
						api.save(user);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					Toast.makeText(getApplicationContext(), EMPTY_ACCOUNT,
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.manage_account:
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more, menu);
		return true;
	}

}
