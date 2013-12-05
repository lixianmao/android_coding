package org.dalian.faceblog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dalian.faceblog_data.API;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MicroblogActivity extends Activity {

	private String text = "";
	private String image = "";
	private String content = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_microblog);
		
		JSONObject user = new API().load();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		try {
			JSONArray blog = user.getJSONArray("blog");
			for (int i = 0; i < blog.length(); i++) {
				JSONObject tweet  = blog.getJSONObject(i);
				HashMap<String, Object> listItem = new HashMap<String, Object>();			//每次都要new一个listItem对象，切记
				listItem.put("item_nickname", tweet.getString("nickname"));
				content = tweet.getString("content");
				
				if(content.indexOf("path:") != -1) {
					image = content.replace("path:", "");
					listItem.put("item_image", BitmapFactory.decodeFile(image));
					listItem.put("item_text", "");
				} else {
					text = content;
					listItem.put("item_image", "");
					listItem.put("item_text", text);
				}
				listItem.put("icon", BitmapFactory.decodeFile(tweet.getString("head")));
				list.add(listItem);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ListView listView = (ListView) findViewById(R.id.lv_all_microblog);
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				R.layout.item_faceblog, new String[] { "icon", "item_nickname",
						"item_text", "item_image" }, new int[] { R.id.iv_blog_head,
						R.id.tv_blog_nickname, R.id.tv_item, R.id.iv_item });
		simpleAdapter.setViewBinder(new ListViewBinder()); 
		listView.setAdapter(simpleAdapter);
	}

	private class ListViewBinder implements android.widget.SimpleAdapter.ViewBinder {  
		  
		@Override
        public boolean setViewValue(View view, Object data,  
                String textRepresentation) {  
            // TODO Auto-generated method stub  
            if((view instanceof ImageView) && (data instanceof Bitmap)) {  
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
		getMenuInflater().inflate(R.menu.microblog, menu);
		return true;
	}

}
