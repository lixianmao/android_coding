package org.dalian.faceblog;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		final Intent intent = new Intent();
		intent.setClass(StartActivity.this, LoginActivity.class);
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity(intent);
			}
		};
		timer.schedule(timerTask, 2000);
	}
	
	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.finish();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
