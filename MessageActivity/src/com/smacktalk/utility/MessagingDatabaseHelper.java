package com.smacktalk.utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.smacktalk.main.SmackTalkMessage;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

public class MessagingDatabaseHelper extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "smackTalkDatabase";
	
	public static final String TABLE_NAME = "TABLE_NAME";
	public static final String COLUMN_ID = "COLUMN_ID";
	public static final String COLUMN_MESSAGE = "COLUMN_MESSAGE";
	public static final String COLUMN_BOOLEAN = "COLUMN_BOOLEAN";
	
	public MessagingDatabaseHelper (Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// onCreate() is to establish the structure of the database and
	//   called by the framework, if the database is accessed but not yet created.
	@Override
	public void onCreate(SQLiteDatabase db){
		
		// .execSQL allows us to execute an in-line SQL statement.
		db.execSQL("create table " + TABLE_NAME + "(" + COLUMN_ID + 
				   " integer primary key autoincrement, " + COLUMN_MESSAGE + 
				   " text not null, " + COLUMN_BOOLEAN + " integer)" );
		Log.v("INFO1", "creating db");
		
	}
	
	// onUpgrade() gives the opportunity to update an existing database structure
	//   or to drop the existing database and recreate it with the onCreate() method.
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		// Implement structure changes and data message here when upgrading
		// Don't think this is necessary since we won't have different structured (versions) of the DB.
		db.execSQL("drop table if exists "+ TABLE_NAME);
		onCreate(db);
	}
	
	//  insertMessage() inserts a new row into the database. 1 row for 1 message.
	public void insertMessage(SmackTalkMessage message){
		
		int boolInt;
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		//  Change the boolean value from True/False to 1/0 to store in the DB.
		if(message.isMine()){
			boolInt = 1;
		}
		else{
			boolInt = 0;
		}
	
		cv.put(COLUMN_MESSAGE, message.getMessage());
		cv.put(COLUMN_BOOLEAN, boolInt);
		
		db.insert(TABLE_NAME, null, cv);
	}
	
	public ArrayList<SmackTalkMessage> getAllMessages(){
		ArrayList<SmackTalkMessage> messageList = new ArrayList<SmackTalkMessage>();
		
		String selectQuery = "SELECT * FROM " +TABLE_NAME;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// iterate through all rows and add to list
		boolean isMineFlag;
		if(cursor.moveToFirst()){
			do{
				SmackTalkMessage message = new SmackTalkMessage();
				message.setID(Integer.parseInt(cursor.getString(0)));
				message.setMessage(cursor.getString(1));
				if(cursor.getString(2).equals("1"))
					isMineFlag = true;
				else{
					isMineFlag = false;
				}
				message.setMine(isMineFlag);
				messageList.add(message);
			} while (cursor.moveToNext());
		}
		return messageList;
	}
	
	public boolean doesDatabaseExist(ContextWrapper context, String dbName) {
	    File dbFile=context.getDatabasePath(dbName);
	    return dbFile.exists();
	}
	public void clearAll(){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, null, null);
	}
	
}
