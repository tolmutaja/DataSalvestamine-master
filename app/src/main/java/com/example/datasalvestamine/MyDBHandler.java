package com.example.datasalvestamine;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static String DATABASE_NAME ="products_db";
    public static final String TABLE_PRODUCTS ="products";
    public static final String COLUMN_ID ="_id";
    public static final String COLUMN_PRODUCTNAME ="productname";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,DATABASE_NAME, factory, DATABASE_VERSION);
    }
/*Igakord kui lood tabeli esimest korda, mida sa tahad et see teeks*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_PRODUCTNAME + " TEXT "+")";
        db.execSQL(query);
    }

/*Siis kui teed upgrade*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*kustutad vana ära ja lood uue tabeli*/
        db.execSQL("DROP TABLE IF EXIST "+ TABLE_PRODUCTS);
        onCreate(db);
    }
    /*Lisad uue veerg/row andmebaasi*/
    public void addProducts(Products products){
        /*different values for different columns; essentially a list of values*/
        ContentValues values = new ContentValues();
        /*product name into that*/
        values.put(COLUMN_PRODUCTNAME, products.get_productName());
        /*db = selle andmebaasiga mdia kirjutame*/
        SQLiteDatabase db = getWritableDatabase();
        /*lisab uue toote tabelisse*/
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
    /*toote kustutamine andmebaasist*/
    public void deleteProduct(String productname){
        SQLiteDatabase db = getWritableDatabase();
        /* DROP kustutab terve tabeli ära, seega kasutame DELETE*/
        /* kustutad tabelist kus toote nimi on võrdne sellega mis sistati*/
        db.execSQL("DELETE FORM "+ TABLE_PRODUCTS+ " WHERE "+COLUMN_PRODUCTNAME + "=\""+productname + "\";");

    }
    /*print out the database as string*/
    public String databaseToString(){
        String dbString ="";
        SQLiteDatabase db = getWritableDatabase();
        String query= "SELECT * FROM "+ TABLE_PRODUCTS + "WHERE 1";

        /*cursor point to a location in your results*/
        Cursor c =db.rawQuery(query, null);
        /*move the first row in your results*/
        c.moveToFirst();
        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("productname"))!=null){
                dbString+= c.getString(c.getColumnIndex("productname"));
                dbString+= "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
