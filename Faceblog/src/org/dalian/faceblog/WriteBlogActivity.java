package org.dalian.faceblog;

import org.dalian.faceblog_data.API;
import org.dalian.faceblog_data.TweetAPI;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class WriteBlogActivity extends Activity {

	private Button buttonSend;
	private Button buttonCancel;
	private ImageButton buttonCamera;
	private ImageButton buttonPhoto;
	private Button buttonAt;
	private EditText textBlog;

	private String imagePath = "";
	private String content = "";
	private String headPath = "";
	private String nickname = "";

	public static final String EMPTY_TWEET = "微博内容为空";
	public static final int RESULT_LOAD_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_blog);

		buttonSend = (Button) findViewById(R.id.btn_send);
		buttonCancel = (Button) findViewById(R.id.btn_cancel);
		buttonCamera = (ImageButton) findViewById(R.id.ibtn_camera);
		buttonPhoto = (ImageButton) findViewById(R.id.ibtn_photo);
		buttonAt = (Button) findViewById(R.id.btn_at);
		textBlog = (EditText) findViewById(R.id.text_microblog);

		Intent intent = getIntent();
		headPath = intent.getStringExtra("head_path");
		nickname = intent.getStringExtra("nickname");

		OnClickListener mOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_send:
					content = textBlog.getText().toString();
					if (content.equals("")) {
						Toast.makeText(getApplicationContext(), EMPTY_TWEET,
								Toast.LENGTH_SHORT).show();
					} else {
						TweetAPI tweetAPI = new TweetAPI();
						API api = new API();
						JSONObject tweet = tweetAPI.createTweet();
						content = tweetAPI.clear(content);
						tweetAPI.editTweet(tweet, headPath, nickname, content); // 编辑微博内容
						JSONObject user = api.load();
						tweetAPI.editBlog(user, tweet); // 将单条微博内容添加到数组当中
						api.save(user);

						Intent intent1 = new Intent(WriteBlogActivity.this,
								PageShowActivity.class);
						startActivity(intent1);
						finish();
					}
					break;
				case R.id.btn_cancel:
					Intent intent2 = new Intent(WriteBlogActivity.this,
							PageShowActivity.class);
					startActivity(intent2);
					break;
				case R.id.ibtn_photo:
					Intent intent3 = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent3, RESULT_LOAD_IMAGE);
					break;
				default:
					break;
				}
			}
		};

		buttonSend.setOnClickListener(mOnClickListener);
		buttonCancel.setOnClickListener(mOnClickListener);
		buttonCamera.setOnClickListener(mOnClickListener);
		buttonPhoto.setOnClickListener(mOnClickListener);
		buttonAt.setOnClickListener(mOnClickListener);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && null != data) {
			Uri selectImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

			imagePath = cursor.getString(columnIndex);
			cursor.close();
		}

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		textBlog.setText("path:" + imagePath); // 路径前面添加标记"path:"
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.write_blog, menu);
		return true;
	}

}
