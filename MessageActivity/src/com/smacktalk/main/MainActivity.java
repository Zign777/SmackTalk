package com.smacktalk.main;

import com.color.speechbubble.R; 
import com.color.speechbubble.R.layout;
import com.color.speechbubble.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.app.Application;

public class MainActivity extends Activity {
	
	public static String myBubble = "";
	public static String yourBubble = "";
	public static String background = "";


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
	
	//@Override
		public boolean onOptionsItemSelected(MenuItem menu) {
		    // Handle item selection
			
			switch (menu.getItemId())
	        {
			case R.id.action_settings:
		        Toast.makeText(MainActivity.this, "Settings is Selected", Toast.LENGTH_SHORT).show();
		        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
			       startActivity(i);
		        return true;
		
		    default:
		        return super.onOptionsItemSelected(menu);
		    }
		}

	public void startChat(View v){
			Intent i = new Intent(MainActivity.this, MessageActivity.class);
			       startActivity(i);

	}

}
