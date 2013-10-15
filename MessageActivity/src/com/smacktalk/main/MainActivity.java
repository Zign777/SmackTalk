package com.smacktalk.main;

import java.io.IOException;

import com.color.speechbubble.R;
import com.color.speechbubble.R.layout;
import com.color.speechbubble.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void startChat(View v){
			Intent i = new Intent(MainActivity.this, MessageActivity.class);
			       startActivity(i);

	}
	public void test(View v){
		NetworkAdapter adapter = new NetworkAdapter(this.getBaseContext());
		try {
			//System.out.println(adapter.getBroadcastAddress().toString());
			adapter.sendPacket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
