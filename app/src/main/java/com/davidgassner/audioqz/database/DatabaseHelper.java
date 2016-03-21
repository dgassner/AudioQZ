package com.davidgassner.audioqz.database;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.davidgassner.audioqz.model.Cue;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "audioqz";
    public static final int DATABASE_VERSION = 1;
    private final String TAG = getClass().getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(getClass().getSimpleName(), "Created database");
        ProjectsTable.createTable(db);
        CuesTable.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Dropped database");
        ProjectsTable.dropTable(db);
        CuesTable.dropTable(db);

        onCreate(db);
    }

    public Cue getCueByIndex(int index) {
        SQLiteDatabase db = getReadableDatabase();
        return CuesTable.getItem(db, index);
    }

    public void seedDatabase() {

        SQLiteDatabase db = getReadableDatabase();

        long cnt = DatabaseUtils.queryNumEntries(db, CuesTable.TABLE_NAME);
        Log.i(TAG, "seedDatabase: number of cues: " + cnt);

        if (cnt == 0) {
            Cue cue = new Cue();
            cue.setCueIndex(1);
            cue.setCueNumber("1");
            cue.setTitle("Charleston");
            cue.setTargetFile("charleston.wav");
            cue.setType(Cue.CUE_TYPE_AUDIO);
            CuesTable.createItem(db, cue);
            Log.i(TAG, "seedDatabase: seeded");
        } else {
            Log.i(TAG, "seedDatabase: not empty");
        }

    }

}
