package com.example.therapyapp.GroundingPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBGroundingHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "groundingdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "grounding";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String ADDRESS_COL = "address";
    private static final String TEXT_COL = "text";
    public DBGroundingHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " STRING,"
                + ADDRESS_COL + " INTEGER,"
                + TEXT_COL + " STRING)";
        db.execSQL(query);
    }
    public void addNewEntry(String name, int address,String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(ADDRESS_COL, address);
        values.put(TEXT_COL, text);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public ArrayList<GroundingModal> readEntries()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorEntries
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<GroundingModal> groundingModalArrayList
                = new ArrayList<>();
        if (cursorEntries.moveToFirst()) {
            do {
                groundingModalArrayList.add(new GroundingModal(
                        cursorEntries.getString(1),
                        cursorEntries.getInt(2),
                        cursorEntries.getString(3)));
            } while (cursorEntries.moveToNext());
        }
        cursorEntries.close();
        return groundingModalArrayList;
    }
    public void deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
