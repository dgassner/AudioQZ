package com.davidgassner.audioqz.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.davidgassner.audioqz.model.Cue;

import java.util.ArrayList;
import java.util.List;

// defines cues table
public class CuesTable {

    // Cues table
    public static final String TABLE_NAME = "cues";

    // Columns
    public static final String CUE_ID = "cueId";
    public static final String CUE_INDEX = "cueIndex";
    public static final String CUE_TITLE = "cueTitle";
    public static final String CUE_FILE = "cueFile";
    public static final String CUE_NUMBER = "cueNumber";
    public static final String CUE_TYPE = "cueType";
    private static final String TAG = "CuesTable";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + CUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CUE_INDEX + " INTEGER,"
                + CUE_TITLE + " TEXT,"
                + CUE_FILE + " TEXT,"
                + CUE_NUMBER + " TEXT,"
                + CUE_TYPE + " TEXT NOT NULL)");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static Cue getItem(SQLiteDatabase db, int cueIndex) {
        Cue cue = null;
        try (Cursor cursor = db.query(TABLE_NAME,
                new String[]{CUE_ID, CUE_INDEX, CUE_NUMBER, CUE_TITLE, CUE_FILE, CUE_TYPE},
                CUE_INDEX + "=?",
                new String[]{String.valueOf(cueIndex)}, null, null, null, null)) {
            if (cursor.moveToNext()) {
                cue = getCueFromCursor(cursor);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItem: " + e.getMessage());
        }
        return cue;
    }

    @NonNull
    private static Cue getCueFromCursor(Cursor cursor) {
        Cue cue = new Cue();
        cue.setCueId(cursor.getInt(cursor.getColumnIndex(CuesTable.CUE_ID)));
        cue.setCueIndex(cursor.getInt(cursor.getColumnIndex(CuesTable.CUE_INDEX)));
        cue.setCueNumber(cursor.getString(cursor.getColumnIndex(CuesTable.CUE_NUMBER)));
        cue.setTitle(cursor.getString(cursor.getColumnIndex(CuesTable.CUE_TITLE)));
        cue.setTargetFile(cursor.getString(cursor.getColumnIndex(CuesTable.CUE_FILE)));
        cue.setType(cursor.getString(cursor.getColumnIndex(CuesTable.CUE_TYPE)));
        return cue;
    }

    public static List<Cue> getAllCues(SQLiteDatabase db) {
        List<Cue> cueList = new ArrayList<>();

        try (Cursor cursor = db.query(TABLE_NAME,
                new String[]{CUE_ID, CUE_INDEX, CUE_NUMBER, CUE_TITLE, CUE_FILE, CUE_TYPE},
                null, null, null, null, CUE_INDEX, null)) {
            while (cursor.moveToNext()) {
                Cue cue = getCueFromCursor(cursor);
                cueList.add(cue);
            }
        } catch (Exception e) {
            Log.e(TAG, "getItem: " + e.getMessage());
        }
        return cueList;
    }

    /**
     * Add a new item
     */
    public static void createItem(SQLiteDatabase db, Cue item) {
        ContentValues values = new ContentValues();
        values.put(CUE_INDEX, item.getCueIndex());
        values.put(CUE_TITLE, item.getTitle());
        values.put(CUE_FILE, item.getTargetFile());
        values.put(CUE_NUMBER, item.getCueNumber());
        values.put(CUE_TYPE, item.getType());

        // add the row
        db.insert(TABLE_NAME, null, values);
    }

}
