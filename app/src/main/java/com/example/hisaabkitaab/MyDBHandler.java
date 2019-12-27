package com.example.hisaabkitaab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler  extends SQLiteOpenHelper {

    private static final int DATABASEVERSION = 1;
    public static final String DATABASE_NAME = "hisaabKitaab.db";
    public static final String TABLE_HISAAB = "hisaabDetail";

    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_MONEY = "_money";
    public static final String COLUMN_DATE = "_date";
    public static final String COLUMN_NOTE = "_note";
    public static final String COLUMN_PHONE ="_phone";
    public static final String COLUMN_STATUS = "_status";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String q = "CREATE TABLE " + TABLE_HISAAB + " ( "+
                COLUMN_NAME + " TEXT , "+
                COLUMN_PHONE + " TEXT ,"+
                COLUMN_MONEY + " INTEGER, "+
                COLUMN_DATE + " DATE,"+
                COLUMN_NOTE + " TEXT, " +
                COLUMN_STATUS +" INTEGER " +
                " );";
        db.execSQL(q);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISAAB);
        onCreate(db);
    }

    public void addHisaab(hisaab h)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,h.getName());
        cv.put(COLUMN_PHONE,h.getPhone());
        cv.put(COLUMN_MONEY,h.getMoney());
        cv.put(COLUMN_NOTE,h.getNote());
        cv.put(COLUMN_DATE,h.getDate());
        cv.put(COLUMN_STATUS,0);

        SQLiteDatabase db= getWritableDatabase();

        db.insert(TABLE_HISAAB,null,cv);
        db.close();
    }

    public ArrayList<hisaab> getHisaabNames()
    {
        ArrayList<hisaab> list = new ArrayList<hisaab>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT "+COLUMN_NAME+", SUM("+COLUMN_MONEY+")as mon , "+COLUMN_PHONE+" FROM "+TABLE_HISAAB + " GROUP BY " + COLUMN_NAME +";";
        //String query = "SELECT * FROM " + TABLE_HISAAB+ " WHERE "+ COLUMN_STATUS +"=0 "+ "ORDER BY " +  COLUMN_NAME + " ASC";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_NAME))!= null)
            {
                String num,name,note,date,money;

                num = c.getString(c.getColumnIndex(COLUMN_PHONE));
                name = c.getString(c.getColumnIndex(COLUMN_NAME));

                //note = c.getString(c.getColumnIndex(COLUMN_NOTE));
                //date = c.getString(c.getColumnIndex(COLUMN_DATE));
                money = c.getString(c.getColumnIndex("mon"));
                int m = Integer.parseInt(money);
                hisaab h = new hisaab(name,num,m,null,null);
                list.add(h);
            }
            c.moveToNext();
        }

        db.close();
        return list;
    }

    public ArrayList<hisaab> getHisaabDetails(String name1)
    {
        ArrayList<hisaab> list = new ArrayList<hisaab>();
        SQLiteDatabase db = getWritableDatabase();
        String q = "select * from " + TABLE_HISAAB+ " where " + COLUMN_NAME +" =\"" + name1 +"\" order by " +
                COLUMN_DATE+" ASC ;";
        Cursor c = db.rawQuery(q,null);
        c.moveToFirst();

        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_NAME))!= null)
            {
                String num,name,note,date,money;

                num = c.getString(c.getColumnIndex(COLUMN_PHONE));
                //name = c.getString(c.getColumnIndex(COLUMN_NAME));

                note = c.getString(c.getColumnIndex(COLUMN_NOTE));
                date = c.getString(c.getColumnIndex(COLUMN_DATE));
                money = c.getString(c.getColumnIndex(COLUMN_MONEY));
                int m = Integer.parseInt(money);
                hisaab h = new hisaab(null,null,m,date,note);
                list.add(h);
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    public void deleteIndividualItemHisaab(String name ,int amt,String date)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE From "+TABLE_HISAAB+" where "+COLUMN_NAME+ "=\""+ name + "\" and "+ COLUMN_MONEY + " =\""+amt+"\" and "+COLUMN_DATE +" =\""+date+"\" ;");
        db.close();
    }


}
