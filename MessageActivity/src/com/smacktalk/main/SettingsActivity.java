package com.smacktalk.main;

import java.util.ArrayList;  
import java.util.List;

import com.color.speechbubble.R;
import com.color.speechbubble.R.layout;
import com.color.speechbubble.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class SettingsActivity extends Activity {

	private Spinner spinner1, spinner2, spinner3;
	private Button btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		addItemsOnSpinner1();
		addItemsOnSpinner2();
		addItemsOnSpinner3();
		addListenerOnButton();
		
	}

	
	  // add items into spinner dynamically
	  public void addItemsOnSpinner1() {
	 
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list = new ArrayList<String>();
		list.add("Pink");
		list.add("Blue");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
	  }
	  
	  public void addItemsOnSpinner2() {
			 
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list = new ArrayList<String>();
		list.add("Green");
		list.add("Red");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter);
		  }
	  public void addItemsOnSpinner3() {
			 
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		List<String> list = new ArrayList<String>();
		list.add("Black");
		list.add("Purple");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(dataAdapter);
		  }
	
	  // get the selected dropdown list value
	  public void addListenerOnButton() {
	 
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		btnSave = (Button) findViewById(R.id.button1);
	 
		btnSave.setOnClickListener(new OnClickListener() {
	 
		  @Override
		  public void onClick(View v) {
			  
			  if(spinner1.getSelectedItem() == "Pink")
			  {
				  MainActivity.myBubble = "Pink"; 
			  }
			  else if(spinner1.getSelectedItem() == "Blue")
			  {
				  MainActivity.myBubble = "Blue";
			  }
			  
			  if(spinner2.getSelectedItem() == "Green")
			  {
				  MainActivity.yourBubble = "Green"; 
			  }
			  else if(spinner2.getSelectedItem() == "Red")
			  {
				  MainActivity.yourBubble = "Red";
			  }
			  
			  if(spinner3.getSelectedItem() == "Black")
			  {
				  MainActivity.background = "Black"; 
			  }
			  else if(spinner3.getSelectedItem() == "Purple")
			  {
				  MainActivity.background = "Purple";
			  }
	 
		    Toast.makeText(SettingsActivity.this,
			"Saved : " + 
	                "\nYour message bubble : "+ String.valueOf(spinner1.getSelectedItem()) + 
	                "\nOther's message bubble : "+ String.valueOf(spinner2.getSelectedItem()) + 
	                "\nBackground : "+ String.valueOf(spinner3.getSelectedItem()),
				Toast.LENGTH_SHORT).show();
		  }
	 
		});
	  }

}
