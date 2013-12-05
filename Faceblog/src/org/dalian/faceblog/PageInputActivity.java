package org.dalian.faceblog;

import org.dalian.faceblog_data.API;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PageInputActivity extends Activity {

	private Button buttonBackground;
	private Button buttonHead;
	private Button buttonOk;
	private RadioGroup mRadioGroup;
	private EditText editNickname;
	private EditText editAge;
	private EditText editFocusNumber;
	private EditText editFansNumber;
	private EditText editIntroduction;
	private EditText editMailbox;
	private EditText editMicroblog;
	private RadioButton boyButton;
	private RadioButton girlButton;

	private ImageView background;
	private ImageView head;

	private String nickname;
	private int gender;
	private int age;
	private int focusNumber;
	private int fansNumber;
	private String introduction;
	private String mailbox;

	private static final int RESULT_LOAD_BACKGROUND = 1;
	private static final int RESULT_LOAD_HEAD = 2;
	private String backgroundPath = "";
	private String headPath = "";

	private Bitmap bmp1;
	private Bitmap bmp2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_input);

		buttonBackground = (Button) this.findViewById(R.id.btn_set_background);
		buttonHead = (Button) this.findViewById(R.id.btn_set_head);
		buttonOk = (Button) this.findViewById(R.id.btn_ok);
		editNickname = (EditText) this.findViewById(R.id.et_nickname);
		mRadioGroup = (RadioGroup) this.findViewById(R.id.et_gender);
		editAge = (EditText) this.findViewById(R.id.et_age);
		editFocusNumber = (EditText) this.findViewById(R.id.et_focus_number);
		editFansNumber = (EditText) this.findViewById(R.id.et_fans_number);
		editIntroduction = (EditText) this.findViewById(R.id.et_introduction);
		editMailbox = (EditText) this.findViewById(R.id.et_mailbox);
		editMicroblog = (EditText) this.findViewById(R.id.et_microblog);

		background = (ImageView) findViewById(R.id.iv_set_backgound);
		head = (ImageView) findViewById(R.id.iv_set_head);

		boyButton = (RadioButton) this.findViewById(R.id.boy);
		girlButton = (RadioButton) this.findViewById(R.id.girl);

		buttonOk.setOnClickListener(mOnClickListener);
		buttonBackground.setOnClickListener(pOnClickListener);
		buttonHead.setOnClickListener(pOnClickListener);

		show();
	}

	private OnClickListener pOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_set_background:
				Intent intent1 = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent1, RESULT_LOAD_BACKGROUND);
				break;
			case R.id.btn_set_head:
				Intent intent2 = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent2, RESULT_LOAD_HEAD);
				break;
			default:
				break;
			}
		}

	};

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

			switch (requestCode) {
			case RESULT_LOAD_BACKGROUND:
				backgroundPath = cursor.getString(columnIndex);
				bmp1 = BitmapFactory.decodeFile(backgroundPath);
				background.setImageBitmap(bmp1);
				break;
			case RESULT_LOAD_HEAD:
				headPath = cursor.getString(columnIndex);
				bmp2 = BitmapFactory.decodeFile(headPath);
				head.setImageBitmap(bmp2);
				break;
			default:
				break;
			}

			cursor.close();
		}

	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(); // 实例化一个intent
			intent.setClass(PageInputActivity.this, PageShowActivity.class); // 跳转到另一个activity
			startActivity(intent);
			finish();
		}

	};

	private void show() {

		API api = new API();
		try {
			JSONObject user = api.load();
			nickname = user.getString("nickname");
			gender = user.getInt("gender");
			age = user.getInt("age");
			focusNumber = user.getInt("focus_number");
			fansNumber = user.getInt("fan_number");
			introduction = user.getString("introduction");
			mailbox = user.getString("mailbox");
			backgroundPath = user.getString("background");
			headPath = user.getString("head");
		} catch (Exception e) {
			// TODO: handle exception
		}

		editNickname.setText(nickname);
		editAge.setText(String.valueOf(age));
		editFocusNumber.setText(String.valueOf(focusNumber));
		editFansNumber.setText(String.valueOf(fansNumber));
		editIntroduction.setText(introduction);
		editMailbox.setText(mailbox);
		boyButton.setChecked(isBoy(gender));
		girlButton.setChecked(!isBoy(gender));
		editMicroblog.setText("welcome to faceblog!");
		if (!headPath.equals("") && !backgroundPath.equals("")) {
			background.setImageBitmap(BitmapFactory.decodeFile(backgroundPath));
			head.setImageBitmap(BitmapFactory.decodeFile(headPath));
		}
	}

	private boolean isBoy(int id) {
		if (id == 0)
			return true;
		else
			return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		nickname = editNickname.getText().toString();
		if (mRadioGroup.getCheckedRadioButtonId() == R.id.boy) {
			gender = 0;
		} else {
			gender = 1;
		}

		try {
			age = Integer.parseInt( // 将EditText中内容转化为整型变量
					editAge.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			focusNumber = Integer.parseInt( // 将EditText中内容转化为整型变量
					editFocusNumber.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			fansNumber = Integer.parseInt( // 将EditText中内容转化为整型变量
					editFansNumber.getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		introduction = editIntroduction.getText().toString();
		mailbox = editMailbox.getText().toString();

		API api = new API();
		JSONObject user = api.load();
		try {
			if (user.getInt("nation") == 0) {
				age = age -1;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		api.editUser(user, nickname, age, gender, introduction, mailbox,
				backgroundPath, headPath, fansNumber, focusNumber);
		api.save(user);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.page_input, menu);
		return true;
	}

}
