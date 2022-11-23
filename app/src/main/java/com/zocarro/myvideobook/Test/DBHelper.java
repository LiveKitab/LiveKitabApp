package com.zocarro.myvideobook.Test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "test_db";
    public static final String CONTACTS_TABLE_NAME = "test";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "testid";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table test " +
                        "(id integer primary key AUTOINCREMENT, testid text,time text)"
        );
        db.execSQL(
                "create table test_data " +
                        "(id integer primary key AUTOINCREMENT, testid text,q_id text,question text,a text,b text,c text,d text,answer text,mark integer)"
        );
       /* db.execSQL(
                "create table stream_data " +
                        "(id integer primary key AUTOINCREMENT, stream_id text,pr_id text,br_id text,term_id text,med text)"
        );*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS test");
        onCreate(db);
    }

    public boolean insertContact (String name,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", name);
        contentValues.put("time", time);
        db.insert("test", null, contentValues);
        return true;
    }
    public boolean inserttest (String testid, String q_id,String question,String a,String b,String c,String d,String answer, int mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", testid);
        contentValues.put("q_id", q_id);
        contentValues.put("question", question);
        contentValues.put("a", a);
        contentValues.put("b", b);
        contentValues.put("c", c);
        contentValues.put("d", d);
        contentValues.put("answer", answer);
        contentValues.put("mark", mark);
        db.insert("test_data", null, contentValues);
        return true;
    }


    public Cursor getData(String testid) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from test where testid='"+testid+"", null );
        Cursor res =  db.rawQuery( "Select * from test Where testid='"+testid+"'", null );
        return res;
    }
    public Cursor getQuestions(String testid,String q_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from test where testid='"+testid+"", null );
        String sql="Select * from test_data Where testid='"+testid+"'"+" AND q_id='"+q_id+"'";
        Cursor res =  db.rawQuery( sql, null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (String name,String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", name);
        contentValues.put("time", time);
        db.update("test", contentValues, " testid= ? ", new String[] { name } );
        return true;
    }
    public boolean updateTest (String testid,String q_id,String answer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", testid);
        contentValues.put("answer", answer);
        db.update("test_data", contentValues, "testid = ? and q_id=?", new String[] { testid,q_id} );
        return true;
    }
    public boolean updateMark (String testid,String q_id,int mark) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("testid", testid);
        contentValues.put("mark", mark);
        db.update("test_data", contentValues, "testid = ? and q_id=?", new String[] { testid,q_id} );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("test",
                "testid = ? ",
                new String[] { Integer.toString(id) });
    }

    /*public ArrayList<TestTimer> getAllCotacts(String testid) {
        ArrayList<TestTimer> array_list = new ArrayList<TestTimer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from test Where testid='"+testid+"'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String tstid=res.getString(res.getColumnIndex("testid"));
            String time=res.getString(res.getColumnIndex("time"));
            array_list.add(new TestTimer(tstid,time));
            res.moveToNext();
        }
        return array_list;
    }*/
    public ArrayList<TestAnswer> getAllTestData(String tst_id) {
        ArrayList<TestAnswer> array_list = new ArrayList<TestAnswer>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from  test_data Where testid='"+tst_id+"'", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String test_id=res.getString(res.getColumnIndex("testid"));
            String q_id=res.getString(res.getColumnIndex("q_id"));
            String answer=res.getString(res.getColumnIndex("answer"));
            int mark= res.getInt(res.getColumnIndex("mark"));
            array_list.add(new TestAnswer(test_id,q_id,answer,mark));
            res.moveToNext();
        }
        return array_list;
    }
}