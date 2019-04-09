package com.example.revobanga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UserDataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "user_info.db";

    private static String INFO_TABLE_NAME = "INFO";

    private final static String KEY_ID = "id";
    private final static String KEY_NAME = "name";
    private final static String KEY_PASSWORD = "pass";

    public UserDataBaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + INFO_TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_PASSWORD + " INTEGER" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS " + INFO_TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public long addEntry(UserEntry entry)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, entry.getName());
        cv.put(KEY_PASSWORD, entry.getPassword());

        long id = db.insert(INFO_TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public UserEntry getEntry(int id)
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;

        cursor = db.query(INFO_TABLE_NAME, new String[] { KEY_ID, KEY_NAME, KEY_PASSWORD}, KEY_ID + "=?", new String[] { Integer.toString(id) }, null, null, null);

        UserEntry entry = new UserEntry();
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                entry.setID(cursor.getInt(0));
                entry.setName(cursor.getString(1));
                entry.setPassword(cursor.getString(2));
            }
        }

        cursor.close();
        db.close();

        return entry;
    }

    public ArrayList<UserEntry> getAllEntries()
    {
        ArrayList<UserEntry> entries = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + INFO_TABLE_NAME + " ORDER BY " + KEY_NAME + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                do {
                    UserEntry entry = new UserEntry();
                    entry.setID(cursor.getInt(0));
                    entry.setName(cursor.getString(1));
                    entry.setPassword(cursor.getString(2));
                    entries.add(entry);
                } while(cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return entries;
    }

    public void deleteEntry(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(INFO_TABLE_NAME, KEY_ID + "=?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void updateEntry(UserEntry entry)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(KEY_NAME, entry.getName());
        cv.put(KEY_PASSWORD, entry.getPassword());

        db.update(INFO_TABLE_NAME, cv, KEY_ID + "=?", new String[] { Integer.toString(entry.getID()) });

        db.close();
    }
}
