package com.example.therapyapp.MonitoringPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "entrydb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "entries";
    private static final String ID_COL = "id";
    private static final String MOOD_COL = "state";
    private static final String CONCEN_COL = "concen";
    private static final String DETERM_COL = "determ";
    private static final String BALANC_COL = "balanc";
    private static final String SELF_COL = "self";
    private static final String DATE_COL = "date";
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MOOD_COL + " INTEGER,"
                + CONCEN_COL + " INTEGER,"
                + DETERM_COL + " INTEGER,"
                + BALANC_COL + " INTEGER,"
                + SELF_COL + " INTEGER,"
                + DATE_COL + " DATE DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(query);
    }
    public void addNewEntry(int mood, int concen, int determ, int balanc, int self) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOOD_COL, mood);
        values.put(CONCEN_COL, concen);
        values.put(DETERM_COL, determ);
        values.put(BALANC_COL, balanc);
        values.put(SELF_COL, self);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<EntryModal> readEntries()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorEntries
                = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<EntryModal> entryModalArrayList
                = new ArrayList<>();
        if (cursorEntries.moveToFirst()) {
            do {
                entryModalArrayList.add(new EntryModal(
                        cursorEntries.getInt(0),
                        cursorEntries.getInt(1),
                        cursorEntries.getInt(2),
                        cursorEntries.getInt(3),
                        cursorEntries.getInt(4),
                        cursorEntries.getInt(5),
                        cursorEntries.getString(6)));
            } while (cursorEntries.moveToNext());
        }
        cursorEntries.close();
        return entryModalArrayList;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void updateEntry(int mood, int concen, int determ, int balanc, int self, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateformatted = simpleDateFormat.format(date);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOOD_COL, mood);
        values.put(CONCEN_COL, concen);
        values.put(DETERM_COL, determ);
        values.put(BALANC_COL, balanc);
        values.put(SELF_COL, self);
        db.update(TABLE_NAME, values, "date LIKE ?", new String[]{dateformatted+"%"});
        db.close();
    }
    public void deleteEntry(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateformatted = simpleDateFormat.format(date);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "date LIKE ?", new String[]{dateformatted+"%"});
        db.close();
    }
}