package com.smacktalk.main;

import java.util.ArrayList;

import com.color.speechbubble.R;




import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

//comment here
public class MessageAdapter extends BaseAdapter {
	ArrayList<SmackTalkMessage> messages;
	Context mContext;

	public MessageAdapter(Context context, ArrayList<SmackTalkMessage> m){
		super();
		mContext = context;
		messages = m;
	}
	@Override
	public int getCount() {
		return messages.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return messages.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SmackTalkMessage message = (SmackTalkMessage) this.getItem(position);

		ViewHolder holder; 
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sms_row, parent, false);
			holder.message = (TextView) convertView.findViewById(R.id.message_text);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		holder.message.setText(message.getMessage());
		
		LayoutParams lp = (LayoutParams) holder.message.getLayoutParams();
		//check if it is a status message then remove background, and change text color.
		if(message.isStatusMessage())
		{
			holder.message.setBackgroundDrawable(null);
			lp.gravity = Gravity.LEFT;
			holder.message.setTextColor(0x7f040001);
			
		}
		else
		{		
			//Check whether message is mine to show green background and align to right
			if(message.isMine())
			{
				if(MainActivity.myBubble == "Pink")
				{
					holder.message.setBackgroundResource(R.drawable.speech_bubble_pink);
				}
				else
				{
					holder.message.setBackgroundResource(R.drawable.speech_bubble_green);
				}
				
				lp.gravity = Gravity.RIGHT;
			}
			//If not mine then it is from sender to show orange background and align to left
			else
			{
				if(MainActivity.yourBubble == "Red")
				{
					holder.message.setBackgroundResource(R.drawable.speech_bubble_orange);
				}
				else
				{
					holder.message.setBackgroundResource(R.drawable.speech_bubble_blue);
				}
				
				lp.gravity = Gravity.LEFT;
			}

			holder.message.setLayoutParams(lp);
			holder.message.setTextColor(0x7f040000);	
		}
		return convertView;
	}
	private static class ViewHolder
	{
		TextView message;
	}

}
