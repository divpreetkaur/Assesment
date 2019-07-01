package com.example.assignment5mvp.data.db;

import android.content.Context;

public class DatabaseUtil {
   private static DataBaseHandler dataBaseHandler;

    static public DataBaseHandler getDataBase(Context context)
    {
        if(dataBaseHandler==null)
        {
            dataBaseHandler=new DataBaseHandler(context);

        }
        return dataBaseHandler;
    }
}
