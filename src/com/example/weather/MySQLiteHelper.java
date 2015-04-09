package com.example.weather;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "itemsDB.db";
	public static final String TABLE_ITEMS = "items";
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TEMP = "temp";
	public static final String COLUMN_HUMIDITY = "humidity";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_CONDITION = "condition";
	public static final String COLUMN_ICON = "icon";
	
	public MySQLiteHelper(Context context, String name, 
			CursorFactory factory, int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
	             TABLE_ITEMS + "("
	             + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_TEMP 
	             + " TEXT," + COLUMN_HUMIDITY + " TEXT, " + COLUMN_TIME
	             + " TEXT," + COLUMN_CONDITION + " TEXT," + COLUMN_ICON + " TEXT)";
	      db.execSQL(CREATE_PRODUCTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
	    onCreate(db);
	}
	public void addItem(Item Item) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TEMP, Item.getTemp());
        values.put(COLUMN_HUMIDITY, Item.getHumidity());
        values.put(COLUMN_TIME, Item.getTime());
        values.put(COLUMN_CONDITION, Item.getCondition());
        values.put(COLUMN_ICON, Item.getIconUrl());
 
        SQLiteDatabase db = this.getWritableDatabase();
        
        db.insert(TABLE_ITEMS, null, values);
        db.close();
}
	public ArrayList<Item> getItems() {
		String query = "Select * FROM " + TABLE_ITEMS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		ArrayList<Item> Items = new ArrayList<Item>();
		
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				Item Item = new Item();
				Item.setId(Integer.parseInt(cursor.getString(0)));
				Item.setTemp(cursor.getString(1));
				Item.setHumidity(cursor.getString(2));
				Item.setTime(cursor.getString(3));
				Item.setCondition(cursor.getString(4));
				Item.setIconUrl(cursor.getString(5));
				Items.add(Item);
                cursor.moveToNext();
			}
			cursor.close();
		}
	    db.close();
		return Items;
	}
	
	public void deleteAll(Context context){
		SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
	    db.delete(TABLE_ITEMS, null, null);
	}
}
