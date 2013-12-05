package org.dalian.faceblog_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TweetAPI {

	public static final String UNKNOWN_HEAD = "";
	public static final String UKONWN_NICKNAME = "";
	public static final String UKNOWN_CONTENT = "";

	public JSONObject createTweet() {
		JSONObject tweet = new JSONObject();
		try {
			tweet.put("head", UNKNOWN_HEAD);
			tweet.put("nickname", UKONWN_NICKNAME);
			tweet.put("content", UKNOWN_CONTENT);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return tweet;
	}

	public void editTweet(JSONObject tweet, String head, String nickname,
			String content) {
		try {
			tweet.put("head", head);
			tweet.put("nickname", nickname);
			tweet.put("content", content);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 初始化系统第一条微博和第一个好友、粉丝
	 */
	public void initBlog() {
		API api = new API();
		JSONObject user = api.load();
		JSONArray blog = new JSONArray();
		JSONArray focus = new JSONArray();
		JSONArray fans = new JSONArray();
		JSONObject firstBlog = new JSONObject();

		try {
			String head = user.getString("head");
			String nickname = user.getString("account");
			editTweet(firstBlog, head, nickname, "welcome to facelog!");
			String friend = "123456";
			String fan = "123456";
			
			blog.put(firstBlog);
			focus.put(friend);
			fans.put(fan);
			
			user.put("blog", blog);
			user.put("focus", focus);
			user.put("fans", fans);
			api.save(user);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void editBlog(JSONObject user, JSONObject tweet) {
		try {
			JSONArray blog = user.getJSONArray("blog");
			blog.put(tweet); // 数组添加新对象元素时一定要new一个object
			user.put("blog", blog);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String clear(String content) {
		if (content.indexOf("我操") != -1) {
			content = content.replace("我操", "你好");
		}
		if (content.indexOf("妈的") != -1) {
			content = content.replace("妈的", "么么嗒");
		}
		return content;
	}

}
