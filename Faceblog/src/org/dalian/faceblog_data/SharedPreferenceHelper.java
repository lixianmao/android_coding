package org.dalian.faceblog_data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceHelper {

	private Context mContext;
	private static final String mName = "microblog";
	
	public SharedPreferenceHelper(Context context) {
		this.mContext = context;
	}
	
	
	public void setStringData(String name, String value) {
		SharedPreferences sp = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(name, value);
		editor.commit();
	}
	public void setIntData(String name, int value) {
		SharedPreferences sp = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(name, value);
		editor.commit();
	}
	public int getIntData(String name) {
		SharedPreferences sp = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
		return sp.getInt(name, -1);
	}
	public String getStringData(String name) {
		SharedPreferences sp = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
		return sp.getString(name,"");
	}
	@SuppressLint("NewApi")
	public void setArrayStringData(String name, ArrayList<String> list) {
		SharedPreferences sp = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		Set<String> set = new HashSet<String>();
		for (String string : list) {
			set.add(string);
		}
		
		editor.putStringSet(name, set);
		editor.commit();
	}
	@SuppressLint("NewApi")
	public String[] getArrayString(String name) {
		SharedPreferences sp = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
		Set<String> set = new HashSet<String>();
		set = sp.getStringSet(name, set);
		String[] list = (String[]) set.toArray(new String[set.size()]);
		return list;
	}
	
	public boolean delete(String s) {
		SharedPreferences sharedPre = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.remove(s);
		return editor.commit();
	}
	public boolean delete(int i) {
		SharedPreferences sharedPre = mContext.getSharedPreferences(mName, Context.MODE_PRIVATE);
		Editor editor = sharedPre.edit();
		editor.remove(String.valueOf(i));
		return editor.commit();
	}
}
