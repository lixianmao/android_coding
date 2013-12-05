package org.dalian.faceblog_data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;
import android.util.Log;

public class API {

	public static String FILE_NAME = "";				//文件名作为全局变量
	public static final String USERS = "users";
	public static final String UNKONWN_STRING = "";
	public static final int UNKNOWN_INT = 0;
	public static final JSONArray UNKNOWN_ARRAY = null;
	public static final JSONObject UNKNOWN_OBJECT = null;

	/**
	 * 从sd卡读取数据
	 * 
	 * @return
	 */
	public JSONObject load() {
		File file = new File(Environment.getExternalStorageDirectory(),
				FILE_NAME);
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String readString = new String();
			while ((readString = reader.readLine()) != null) {
				sb.append(readString);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			JSONObject object = new JSONObject(sb.toString());
			return object;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 存储数据信息
	 * 
	 * @param jsonData
	 */
	public void save(JSONObject object) {
		if (Environment.getExternalStorageState() == null
				&& Environment.getExternalStorageState().equalsIgnoreCase(
						"removed")) {
			Log.v("sdCard", "SD卡不存在或被移除");
		}
//		try {
//			FILE_NAME = object.getString("account") + ".json";			//创建文件
//		} catch (JSONException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		File file = new File(Environment.getExternalStorageDirectory(),
				FILE_NAME);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new StringReader(
					object.toString()));
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			int len = 0;
			char[] buffer = new char[1024];
			while ((len = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, len);
			}
			writer.flush();
			writer.close();
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加用户信息到文件
	 */
	public void addAccount(JSONObject jsonObject) {

		try {
			JSONObject object = this.load();
			JSONArray array = object.getJSONArray(USERS);
			array.put(jsonObject); // 添加新帐号到数组当中
			JSONObject newObject = new JSONObject().put(USERS, array);
			save(newObject); // 保存新数组
		} catch (JSONException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}

	public JSONObject findAccount(String ac, String pw) {
		try {
			JSONObject object = this.load();
			if (ac.equals(object.getString("account"))
					&& pw.equals(object.getString("password"))) {
				return object;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public JSONObject createUser() {
		JSONObject user = new JSONObject();
		try {
			user.put("password", UNKONWN_STRING);
			user.put("account", UNKONWN_STRING);
			user.put("nickname", UNKONWN_STRING);
			user.put("age", UNKNOWN_INT);
			user.put("gender", UNKNOWN_INT);
			user.put("nation", UNKNOWN_INT);
			user.put("fans_number", UNKNOWN_INT);
			user.put("focus_number", UNKNOWN_INT);
			user.put("blog_number", UNKNOWN_INT);
			user.put("introduction", UNKONWN_STRING);
			user.put("mailbox", UNKONWN_STRING);
			user.put("background", UNKONWN_STRING);
			user.put("head", UNKONWN_STRING);
			user.put("focus", UNKNOWN_ARRAY);
			user.put("fans", UNKNOWN_ARRAY);
			user.put("blog", UNKNOWN_ARRAY);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}

	public void registerUser(JSONObject user, String account, String password,
			int nation) {
		try {
			user.put("account", account);
			user.put("password", password);
			user.put("nation", nation);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void editUser(JSONObject user, String nickname, int age, int gender,
			String introduction, String mailbox, String background,
			String head, int fansNumber, int focusNumber) {
		try {
			user.put("nickname", nickname);
			user.put("age", age);
			user.put("gender", gender);
			user.put("introduction", introduction);
			user.put("mailbox", mailbox);
			user.put("background", background);
			user.put("head", head);
			user.put("fan_number", fansNumber);
			user.put("focus_number", focusNumber);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * 在登录或注册时判断帐号是否存在，同时给静态变量FILE_NAME赋值
	 * @param account
	 * @return
	 */
	public boolean accountExist(String account) {
		FILE_NAME = account + ".json";
		File file = new File(Environment.getExternalStorageDirectory(),
				FILE_NAME);
		return file.exists();
	}
	/**
	 * 查找其他用户时判断帐号是否存在
	 * @param account
	 * @return
	 */
	public boolean isExist(String account) {
		String name = account + ".json";
		File file = new File(Environment.getExternalStorageDirectory(),
				name);
		return file.exists();
	}
	/**
	 * 根据帐号获取用户信息
	 * @param account
	 * @return
	 */
	public  JSONObject getUser(String account) {
		File file = new File(Environment.getExternalStorageDirectory(), account + ".json");
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String readString = new String();
			while ((readString = reader.readLine()) != null) {
				sb.append(readString);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			JSONObject object = new JSONObject(sb.toString());
			return object;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
