package org.dalian.faceblog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dalian.faceblog_data.API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FansActivity extends Activity {

	private ListView listView;
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fans);
		listView = (ListView) findViewById(R.id.lv_fans);

		API api = new API();
		JSONObject user = api.load();
		try {
			JSONArray fans = user.getJSONArray("fans");
			for (int i = 0; i < fans.length(); i++) {
				String account = fans.getString(i);
				JSONObject friend = api.getUser(account);
				String nickname = friend.getString("nickname");
				String head = friend.getString("head");

				HashMap<String, Object> listItem = new HashMap<String, Object>(); // 每次都要new一个listItem对象，切记
				listItem.put("item_nickname", nickname);
				listItem.put("item_head", BitmapFactory.decodeFile(head));
				list.add(listItem);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				R.layout.item_fans, new String[] { "item_head",
						"item_nickname", }, new int[] { R.id.iv_fans_head,
						R.id.tv_fans_nickname });
		simpleAdapter.setViewBinder(new ListViewBinder());
		listView.setAdapter(simpleAdapter);
	}

	private class ListViewBinder implements
			android.widget.SimpleAdapter.ViewBinder {

		@Override
		public boolean setViewValue(View view, Object data,
				String textRepresentation) {
			// TODO Auto-generated method stub
			if ((view instanceof ImageView) && (data instanceof Bitmap)) {
				ImageView imageView = (ImageView) view;
				Bitmap bmp = (Bitmap) data;
				imageView.setImageBitmap(bmp);
				return true;
			}
			return false;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fans, menu);
		return true;
	}
}
