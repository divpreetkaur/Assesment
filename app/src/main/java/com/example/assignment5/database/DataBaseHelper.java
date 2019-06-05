package com.example.assignment5.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.assignment5.utilities.Constants;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    static Constants constants = new Constants();
    public static final String DATABASE_NAME = constants.DATABASE_NAME;
    public static final String TABLE_NAME =constants.TABLE_NAME;
    public static final String COL_1 =constants.COL_NAME;
    public static final String COL_2 =constants.COL_ROLLNO;
    public static final String COL_3 =constants.COL_CLS;
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
     //Creating database table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " TEXT," + COL_2 + " TEXT PRIMARY KEY,"
                + COL_3 + " TEXT" + ")";
        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String rollno, String cls) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, rollno);
        contentValues.put(COL_3,cls);
       long result=db.insert(TABLE_NAME, null, contentValues);
       if(result==-1)
           return false;
       else
       return true;
    }
    public boolean updateData(String name,String rollno,String cls,String oldRollNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,rollno);
        contentValues.put(COL_3,cls);
        db.update(TABLE_NAME, contentValues, constants.COL_ROLLNO + " = ?",new String[] {oldRollNumber});
        return true;
    }
    public Integer deleteData (String name,String rollno,String cls) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, constants.COL_ROLLNO + " = ?",new String[] { rollno});
    }

    public Cursor getAllData()
    { SQLiteDatabase db = this.getReadableDatabase();

        Cursor res=null;
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        if(count!=0) {
             res = db.rawQuery("select * from " + TABLE_NAME, null);
        }

        return res;
    }
}
