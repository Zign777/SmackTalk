package com.smacktalk.main;

import java.util.ArrayList;
import java.util.Random;




import com.color.speechbubble.R;
import com.color.speechbubble.R.id;
import com.color.speechbubble.R.layout;
import com.smacktalk.utility.BroadcastChatService;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MessageActivity extends ListActivity {
	/** Called when the activity is first created. */

	ArrayList<SmackTalkMessage> messages;
	MessageAdapter adapter;
	EditText text;
	static Random rand = new Random();	
	static String sender;
	private static final String TAG = "BcastChat";
	private static final boolean D = true;

	    // Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_READ 	= 1;
	public static final int MESSAGE_WRITE 	= 2;
	public static final int MESSAGE_TOAST 	= 3;
	private BroadcastChatService mChatService = null;
	private StringBuffer mOutStringBuffer;

	    // Key names received from the BroadcastChatService Handler
	public static final String TOAST = "toast";
	
	private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	
        	if(D) Log.e(TAG, "[handleMessage !!!!!!!!!!!! ]");
        	
            switch (msg.what) {
            
	            case MESSAGE_WRITE:
	            	
	                byte[] writeBuf = (byte[]) msg.obj;
	                // construct a string from the buffer
	                String writeMessage = new String(writeBuf);
	                addNewMessage(new SmackTalkMessage("Me: "+writeMessage, true));
	                break;
	            case MESSAGE_READ:
	                String readBuf = (String) msg.obj;
	                addNewMessage(new SmackTalkMessage("You: "+readBuf, false));
	                break;               
	            case MESSAGE_TOAST:
	                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
	                               Toast.LENGTH_SHORT).show();
	                break;
            }
        }
    };    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		text = (EditText) this.findViewById(R.id.text);
		
		sender = Utility.sender[rand.nextInt( Utility.sender.length-1)];
		this.setTitle("SmackTalk");
		messages = new ArrayList<SmackTalkMessage>();

		messages.add(new SmackTalkMessage("Hello"+"\n - Etai", true));
		messages.add(new SmackTalkMessage("Hi!"+"\n - Joelle", false));
		messages.add(new SmackTalkMessage("What's up"+"\n - John", false));
		

		adapter = new MessageAdapter(this, messages);
		setListAdapter(adapter);
		  // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BroadcastChatService(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
	
	}
	@Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");
        
        mChatService.start();
    }
	public void sendMessage(View v)
	{
		String newMessage = text.getText().toString().trim(); 
		if(D) Log.e(TAG, "[sendMessage]");
    	
        // Check that there's actually something to send
        if (newMessage.length() > 0 ) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = newMessage.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            text.setText(mOutStringBuffer);
            
        }
	}
	private class SendMessage extends AsyncTask<Void, String, String>
	{
		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(2000); //simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			this.publishProgress(String.format("%s started writing", sender));
			try {
				Thread.sleep(2000); //simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.publishProgress(String.format("%s has entered text", sender));
			try {
				Thread.sleep(3000);//simulate a network call
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			return Utility.messages[rand.nextInt(Utility.messages.length-1)];
			
			
		}
		@Override
		public void onProgressUpdate(String... v) {
			
			if(messages.get(messages.size()-1).isStatusMessage)//check wether we have already added a status message
			{
				messages.get(messages.size()-1).setMessage(v[0]); //update the status for that
				adapter.notifyDataSetChanged(); 
				getListView().setSelection(messages.size()-1);
			}
			else{
				addNewMessage(new SmackTalkMessage(true,v[0])); //add new message, if there is no existing status message
			}
		}
		@Override
		protected void onPostExecute(String text) {
			if(messages.get(messages.size()-1).isStatusMessage)//check if there is any status message, now remove it.
			{
				messages.remove(messages.size()-1);
			}
			
			addNewMessage(new SmackTalkMessage(text, false)); // add the orignal message from server.
		}
		

	}
	void addNewMessage(SmackTalkMessage m)
	{
		messages.add(m);
		adapter.notifyDataSetChanged();
		getListView().setSelection(messages.size()-1);
	}
}