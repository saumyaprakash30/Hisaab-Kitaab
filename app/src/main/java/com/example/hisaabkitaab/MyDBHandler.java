package com.example.hisaabkitaab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler  extends SQLiteOpenHelper {

    private static final int DATABASEVERSION = 1;
    public static final String DATABASE_NAME = "hisaabKitaab.db";
    public static final String TABLE_HISAAB = "hisaabDetail";

    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_MONEY = "_money";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_NOTE = "_note";
    public static final String COLUMN_PHONE ="_phone";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String q = "CREATE TABLE " + TABLE_HISAAB + " ( "+
                COLUMN_NAME + " TEXT , "+
                COLUMN_PHONE + "TEXT ,"+
                COLUMN_MONEY + " INTEGER, "+
                COLUMN_DATE + " DATE,"+
                COLUMN_NOTE + " TEXT );";
        db.execSQL(q);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISAAB);
        onCreate(db);
    }



}
