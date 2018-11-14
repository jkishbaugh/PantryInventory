package com.example.jkishbaugh.pantryinventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.jkishbaugh.pantryinventory.data.PantryContract.PantryEntry;

public class PantryDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = PantryDbHelper.class.getSimpleName();
    public static final String DATABASE_NAME= "product.db";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_PANTRY_TABLE= "CREATE TABLE pantry(_ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, price TEXT NOT NULL, QUANTITY INTEGER DEFAULT 1, Supplier INTEGER DEFAULT 0, Number TEXT);";

    public PantryDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_PANTRY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
