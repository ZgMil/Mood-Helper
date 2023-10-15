package com.example.therapyapp.CardPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBCardHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "carddb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "cards";
    private static final String ID_COL = "id";
    private static final String GROUPS_COL = "groups";
    private static final String TEXT_COL = "text";
    public DBCardHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GROUPS_COL + " STRING,"
                + TEXT_COL + " STRING)";
        db.execSQL(query);
    }

    public void addNewEntry(String groups,String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUPS_COL, groups);
        values.put(TEXT_COL, text);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<CardModal> readEntries()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorEntries
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<CardModal> cardModalArrayList
                = new ArrayList<>();
        if (cursorEntries.moveToFirst()) {
            do {
                cardModalArrayList.add(new CardModal(
                        cursorEntries.getInt(0),
                        cursorEntries.getString(1),
                        cursorEntries.getString(2)));
            } while (cursorEntries.moveToNext());
        }
        cursorEntries.close();
        return cardModalArrayList;
    }

    public void updatecard( String text, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEXT_COL, text);
        db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void updateGroup(String group, String newgroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUPS_COL, newgroup);
        db.update(TABLE_NAME, values, "groups = ?", new String[]{String.valueOf(group)});
        db.close();
    }
    public void deleteGroup(String group) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "groups = ?", new String[]{String.valueOf(group)});
        db.close();
    }
    public void deleteCard(int id) {
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
