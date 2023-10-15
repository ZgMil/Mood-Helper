package com.example.therapyapp.InfoPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBInfoHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "infodb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "info";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String TEXT_COL = "text";
    private static final String TAG_COL = "tag";

    public DBInfoHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " STRING,"
                + TEXT_COL + " STRING,"
                + TAG_COL + " INTEGER)";
        db.execSQL(query);
    }
    public void addNewEntry(String name, String text, int tag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(TEXT_COL, text);
        values.put(TAG_COL, text);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<InfoModal> readEntries()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorEntries
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<InfoModal> infoModalArrayList
                = new ArrayList<>();
        if (cursorEntries.moveToFirst()) {
            do {
                infoModalArrayList.add(new InfoModal(
                        cursorEntries.getString(1),
                        cursorEntries.getString(2),
                        cursorEntries.getInt(3)));
            } while (cursorEntries.moveToNext());
        }
        cursorEntries.close();
        return infoModalArrayList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
