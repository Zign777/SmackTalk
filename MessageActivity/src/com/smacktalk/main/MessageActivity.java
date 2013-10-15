package com.smacktalk.main;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Random;

import name.giacomofurlan.udpmessenger.UDPMessenger;

import com.color.speechbubble.R;
import com.color.speechbubble.R.id;
import com.color.speechbubble.R.layout;
import com.smacktalk.utility.Messenger;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MessageActivity extends ListActivity {
	/** Called when the activity is first created. */

	ArrayList<Message> messages;
	MessageAdapter adapter;
	EditText text;
	static Random rand = new Random();	
	static String sender;
	public static final String SERVERIP = "127.0.0.1"; // 'Within' the emulator!
    public static final int SERVERPORT = 4444;
    boolean start;
    NetworkMessage messenger;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		text = (EditText) this.findViewById(R.id.text);
		
		sender = Utility.sender[rand.nextInt( Utility.sender.length-1)];
		this.setTitle("SmackTalk");
		messages = new ArrayList<Message>();

		messages.add(new Message("Hello"+"\n - Etai", true));
		messages.add(new Message("Hi!"+"\n - Joelle", false));
		messages.add(new Message("What's up"+"\n - John", false));
		start = false;
		
		adapter = new MessageAdapter(this, messages);
		setListAdapter(adapter);
		messenger = new NetworkMessage(this.getApplicationContext(),"test", SERVERPORT);
		messenger.startMessageReceiver();
	
	}
	public void sendMessage(View v)
	{
		String newMessage = text.getText().toString().trim(); 
		System.out.println("I'm here");
		if(newMessage.length() > 0)
		{
			messenger.sendMessage(newMessage);
			addNewMessage(new Message(newMessage, true));
			
			start = true;
		}
	}
	
	void addNewMessage(Message m)
	{
		messages.add(m);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
	}
	private class NetworkMessage extends UDPMessenger{

		public NetworkMessage(Context context, String tag, int multicastPort)
				throws IllegalArgumentException {
			super(context, tag, multicastPort);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Runnable getIncomingMessageAnalyseRunnable() {
			Runnable incoming = new Runnable(){

				@Override
				public void run() {
					addNewMessage(new com.smacktalk.main.Message(incomingMessage.getMessage(), false));
					System.out.println(incomingMessage.getMessage());
					
				}
				
			};
			return incoming;
		}
		public String getMessage(){
			return incomingMessage.getMessage();
		}
		
	}
}