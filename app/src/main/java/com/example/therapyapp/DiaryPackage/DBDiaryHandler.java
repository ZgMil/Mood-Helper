package com.example.therapyapp.DiaryPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBDiaryHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "diarydb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "diaryentries";
    private static final String ID_COL = "id";
    private static final String TEXT_COL = "state";
    private static final String DATE_COL = "date";
    public DBDiaryHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TEXT_COL + " STRING,"
                + DATE_COL + " DATE DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(query);
    }
    public void addNewEntry(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEXT_COL, text);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<DiaryModal> readEntries()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorEntries
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<DiaryModal> diaryModalArrayList
                = new ArrayList<>();
        if (cursorEntries.moveToFirst()) {
            do {
                diaryModalArrayList.add(new DiaryModal(
                        cursorEntries.getInt(0),
                        cursorEntries.getString(1),
                        cursorEntries.getString(2)));
            } while (cursorEntries.moveToNext());
        }
        cursorEntries.close();
        return diaryModalArrayList;
    }
    public void updatediary( String text, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEXT_COL, text);
        db.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteDiary(int id) {
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
