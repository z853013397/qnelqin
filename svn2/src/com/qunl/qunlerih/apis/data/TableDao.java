package com.qunl.qunlerih.apis.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qunl.qunlerih.dao.TableListItem;



public class TableDao {
	public static final String TABLE_NAME = "tables";
	public static final String COLUMN_NAME_TID = "tid";
	public static final String COLUMN_NAME_UID = "uid";
	public static final String COLUMN_NAME_OWNERID = "ownerid";
	public static final String COLUMN_NAME_TNAME = "tname";
	public static final String COLUMN_NAME_ANONY = "anonymous";

	private DbOpenHelper dbHelper;

	public TableDao(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	/**
	 * 保存table list
	 * 
	 * @param tableList
	 */
	public void saveTableList(List<TableListItem> tableList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if (db.isOpen()) {
			db.delete(TABLE_NAME, null, null);
			
			for (TableListItem table : tableList) {
				
				ContentValues values = new ContentValues();
				values.put(COLUMN_NAME_TID, table.getTid());
				if(table.getTname() != null)
					values.put(COLUMN_NAME_TNAME, table.getTname());
				if(table.getAnonymous() != null)
					values.put(COLUMN_NAME_ANONY, table.getAnonymous());
				if(table.getOwnerid() != null)
				    values.put(COLUMN_NAME_OWNERID, table.getOwnerid());
				if(table.getUid() != null)
				    values.put(COLUMN_NAME_UID, table.getUid());
				db.replace(TABLE_NAME, null, values);
			}
		}
	}

	/**
	 * 获取table list
	 * 
	 * @return
	 */
	public Map<String, TableListItem> getTableList() {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Map<String, TableListItem> tables = new HashMap<String, TableListItem>();
		if (db.isOpen()) {
			Cursor cursor = db.rawQuery("select * from " + TABLE_NAME /* + " desc" */, null);
			while (cursor.moveToNext()) {
				String tname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TNAME));
				String tid = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TID));
				String uid = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_UID));
				String anony = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ANONY));
				String ownerid = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_OWNERID));
				TableListItem table = new TableListItem();
				table.setTname(tname);
				table.setTid(tid);
				table.setOwnerid(ownerid);
				table.setUid(uid);
				tables.put(tid, table);
			}
			cursor.close();
		}
		return tables;
	}
	
	/**
	 * 删除一个table
	 * @param tid
	 */
	public void deleteTable(String tid){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		if(db.isOpen()){
			db.delete(TABLE_NAME, COLUMN_NAME_TID + " = ?", new String[]{tid});
		}
	}
	
	/**
	 * 保存一个table
	 * @param user
	 */
	public void saveTable(TableListItem table){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_TID, table.getTid());
		if(table.getTname() != null)
			values.put(COLUMN_NAME_TNAME, table.getTname());
		if(table.getAnonymous() != null)
			values.put(COLUMN_NAME_ANONY, table.getAnonymous());
		if(table.getOwnerid() != null)
		    values.put(COLUMN_NAME_OWNERID, table.getOwnerid());
		if(table.getUid() != null)
		    values.put(COLUMN_NAME_UID, table.getUid());
		if(db.isOpen()){
		db.replace(TABLE_NAME, null, values);
		}
		
		
	}

}
