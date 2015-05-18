package com.qunl.qunlerih.apis.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DbOpenHelper  extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	private static DbOpenHelper instance;

	private static final String QUNLE_TABLE_CREATE = "CREATE TABLE "
			+ TableDao.TABLE_NAME + " ("
			+ TableDao.COLUMN_NAME_TNAME + " TEXT, "
			+ TableDao.COLUMN_NAME_ANONY + " TEXT, "
			+ TableDao.COLUMN_NAME_OWNERID + " TEXT, "
			+ TableDao.COLUMN_NAME_UID + " TEXT, "
			+ TableDao.COLUMN_NAME_TID + " TEXT PRIMARY KEY);";
	
	
	
	private DbOpenHelper(Context context) {
		super(context, getUserDatabaseName(), null, DATABASE_VERSION);
	}
	
	public static DbOpenHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DbOpenHelper(context.getApplicationContext());
		}
		return instance;
	}
	
	private static String getUserDatabaseName() {
        return  "qunle_rih.db";
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(QUNLE_TABLE_CREATE);
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void closeDB() {
	    if (instance != null) {
	        try {
	            SQLiteDatabase db = instance.getWritableDatabase();
	            db.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        instance = null;
	    }
	}
	
}

