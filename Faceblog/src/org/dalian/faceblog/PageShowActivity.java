package org.dalian.faceblog;

import org.dalian.faceblog_data.API;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PageShowActivity extends Activity {

	private String nickname;
	private int gender;
	private String introduction;
	private int focusNumber;
	private int fansNumber;
	private int blogNumber;

	private TextView showNickname;
	private ImageView showGender;
	private Button showFocusNumber;
	private Button showFansNumber;
	private TextView showIntroduction;
	private Button showBlogNumber;
	private Button moreInfoButton;
	private Button moreButton;
	private Button writeButton;
	private Button friendButton;

	private ImageView background;
	private ImageButton head;
	private String backgroundPath = "";
	private String headPath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_show);

		showNickname = (TextView) findViewById(R.id.tv_nickname);
		showGender = (ImageView) findViewById(R.id.iv_gender);
		showFocusNumber = (Button) findViewById(R.id.btn_focus_number);
		showFansNumber = (Button) findViewById(R.id.btn_fans_number);
		showIntroduction = (TextView) findViewById(R.id.tv_introduction);
		showBlogNumber = (Button) findViewById(R.id.btn_microblog_number);
		background = (ImageView) findViewById(R.id.iv_show_background);
		head = (ImageButton) findViewById(R.id.ibtn_show_head);
		moreInfoButton = (Button) findViewById(R.id.btn_more_info);
		moreButton = (Button) findViewById(R.id.btn_more);
		writeButton = (Button) findViewById(R.id.btn_write_microblog);
		friendButton = (Button) findViewById(R.id.btn_friends_list);

		moreInfoButton.setOnClickListener(mOnClickListener);
		showBlogNumber.setOnClickListener(mOnClickListener);
		showFansNumber.setOnClickListener(mOnClickListener);
		showFocusNumber.setOnClickListener(mOnClickListener);
		moreButton.setOnClickListener(mOnClickListener);
		writeButton.setOnClickListener(mOnClickListener);
		friendButton.setOnClickListener(mOnClickListener);

	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		Intent intent = new Intent();

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_friends_list:
				intent.setClass(PageShowActivity.this, FansActivity.class);
				break;
			case R.id.btn_write_microblog:
				intent.setClass(PageShowActivity.this, WriteBlogActivity.class);
				intent.putExtra("head_path", headPath);
				intent.putExtra("nickname", nickname);
				break;
			case R.id.btn_more_info:
				intent.setClass(PageShowActivity.this,
						MoreInformationActivity.class);
				break;
			case R.id.btn_focus_number:
				intent.setClass(PageShowActivity.this, FocusActivity.class);
				break;
			case R.id.btn_fans_number:
				intent.setClass(PageShowActivity.this, FansActivity.class);
				break;
			case R.id.btn_microblog_number:
				intent.setClass(PageShowActivity.this, MicroblogActivity.class);
				break;
			case R.id.btn_more:
				intent.setClass(PageShowActivity.this, MoreActivity.class);
				break;
			default:
				break;
			}
			startActivity(intent);
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			JSONObject user = new API().load();
			nickname = user.getString("nickname");
			gender = user.getInt("gender");
			focusNumber = user.getInt("focus_number");
			fansNumber = user.getInt("fan_number");
			introduction = user.getString("introduction");
			backgroundPath = user.getString("background");
			headPath = user.getString("head");
			blogNumber = user.getJSONArray("blog").length();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		showNickname.setText(nickname); // 显示昵称
		switch (gender) { // 显示性别
		case 0:
			showGender.setImageResource(R.drawable.boy);
			break;
		case 1:
			showGender.setImageResource(R.drawable.girl);
		default:
			break;
		}
		showIntroduction.setText(introduction); // 显示简介
		showFocusNumber.setText(focusNumber + "\n" + "关注"); // 显示关注数
		showFansNumber.setText(fansNumber + "\n" + "粉丝"); // 显示粉丝数
		showBlogNumber.setText(blogNumber + "\n" + "微博");

		if (backgroundPath != "")
			background.setImageBitmap(BitmapFactory.decodeFile(backgroundPath)); // 根据路径显示背景图片
		else
			background.setImageResource(R.drawable.background);
		if (headPath != "")
			head.setImageBitmap(BitmapFactory.decodeFile(headPath)); // 根据路径显示背景图片
		else
			head.setImageResource(R.drawable.head);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.page_show, menu);
		return true;
	}

}
