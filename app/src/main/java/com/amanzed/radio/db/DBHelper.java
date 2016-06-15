package com.amanzed.radio.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.amanzed.radio.list.*;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "MyDBName.db";
   public static final String RADIO_TABLE_NAME = "radio";
   public static final String COUNTRY_TABLE_NAME = "country";
   public static final String RADIO_COLUMN_ID = "id";
   public static final String RADIO_COLUMN_NAME = "name";
   public static final String RADIO_COLUMN_URL = "url";
   public static final String RADIO_COLUMN_WEBSITE = "website";
   public static final String RADIO_COLUMN_ABOUT = "about";
   public static final String RADIO_COLUMN_CCODE = "country";
   private HashMap hp;

   public DBHelper(Context context)
   {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      db.execSQL("create table radio (id INTEGER PRIMARY KEY AUTOINCREMENT, "
      		+ "name TEXT UNIQUE, "
      + "url TEXT UNIQUE, "
      + "website, "
      + "about, "
      + "country TEXT NOT NULL)");
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS radio");
      onCreate(db);
   }
   
   public boolean insertRadio  (String name, String url, String website, String about,String country)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("name", name);
      contentValues.put("url", url);
      contentValues.put("website", website);	
      contentValues.put("about", about);
      contentValues.put("country", country);
      db.insert("radio", null, contentValues);
      return true;
   }
   
   public boolean insertRadioWithID  (String id, String name, String url, String website, String about,String country)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("id", id);
      contentValues.put("name", name);
      contentValues.put("url", url);
      contentValues.put("website", website);	
      contentValues.put("about", about);
      contentValues.put("country", country);
      db.insert("radio", null, contentValues);
      return true;
   }
   
   public Cursor getRadioData(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from radio where id="+id+"", null );
      return res;
   }
   
   public int numberOfRadioRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, RADIO_TABLE_NAME);
      return numRows;
   }
   
   public boolean updateRadio (Integer id, String name, String url, String website, String about,String country)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("name", name);
      contentValues.put("url", url);
      contentValues.put("website", website);	
      contentValues.put("about", about);
      contentValues.put("country", country);
      db.update("radio", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
      return true;
   }
   
   public void deleteAll(){
	   this.getWritableDatabase().execSQL("delete from radio");
   }

   public Integer deleteRadio (Integer id)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("radio", 
      "id = ? ", 
      new String[] { Integer.toString(id) });
   }
   
   public ArrayList<Radio> getAllRadios()
   {
      ArrayList<Radio> array_list = new ArrayList<Radio>();
      
      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from radio", null );
      res.moveToFirst();
      
      while(res.isAfterLast() == false){
    	 Radio rd = new Radio();
    	 rd.setId(Integer.valueOf(res.getString(res.getColumnIndex(RADIO_COLUMN_ID))));
    	 rd.setName(res.getString(res.getColumnIndex(RADIO_COLUMN_NAME)));
    	 rd.setCountry(res.getString(res.getColumnIndex(RADIO_COLUMN_CCODE)));
    	 rd.setUrl(res.getString(res.getColumnIndex(RADIO_COLUMN_URL)));
    	 rd.setAbout(res.getString(res.getColumnIndex(RADIO_COLUMN_ABOUT)));
    	 rd.setWebsite(res.getString(res.getColumnIndex(RADIO_COLUMN_WEBSITE)));
         array_list.add(rd);
         res.moveToNext();
      }
      return array_list;
   }
   

}