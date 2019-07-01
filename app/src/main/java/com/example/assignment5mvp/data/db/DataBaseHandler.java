package com.example.assignment5mvp.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.assignment5mvp.constant.Constants;
import com.example.assignment5mvp.data.model.Student;

import java.io.Serializable;
import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper implements Serializable {
    static Constants constants = new Constants();
    Student student=new Student();
    ArrayList<Student> arrayList;
    ArrayList<String> rollnoArrayList=new ArrayList<>();
    public static final String DATABASE_NAME = constants.DATABASE_NAME;
    public static final String TABLE_NAME =constants.TABLE_NAME;
    public static final String COL_1 =constants.COL_NAME;
    public static final String COL_2 =constants.COL_ROLLNO;
    public static final String COL_3 =constants.COL_CLS;
    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 2);
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

    public boolean insertData(final Student student) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, student.getName());
        contentValues.put(COL_2, student.getRollno());
        contentValues.put(COL_3,student.getClasses());
        long result=db.insert(TABLE_NAME, null, contentValues);
        if(result>=1)
            return true;
        else
            return false;
    }

    public boolean updateData(final Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,student.getName());
        contentValues.put(COL_2,student.getRollno());
        contentValues.put(COL_3,student.getClasses());
        long result=db.update(TABLE_NAME, contentValues, constants.COL_ROLLNO + " = ?",new String[] {student.getRollno()});
        if(result>=1)
            return true;
        else
            return false;

    }

    public boolean deleteData (final String rollno) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result=db.delete(TABLE_NAME, constants.COL_ROLLNO + " = ?",new String[] { rollno});
        if(result>=1)
            return true;
        else
            return false;
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

    public ArrayList<Student> getListElements()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=getAllData();
        arrayList=new ArrayList<>();
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                arrayList.add(new Student(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2)));



            }
        }
        return arrayList;
    }
}

