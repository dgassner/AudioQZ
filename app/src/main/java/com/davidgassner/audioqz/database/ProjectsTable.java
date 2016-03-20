package com.davidgassner.audioqz.database;

import android.database.sqlite.SQLiteDatabase;

// defines projects table
public class ProjectsTable {

    //  Projects table constants
    public static final String TABLE_NAME = "projects";

    //  Column names
    public static final String PROJECT_ID = "projectId";
    public static final String PROJECT_NAME = "projectName";

    public static void createTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + PROJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PROJECT_NAME + " TEXT UNIQUE NOT NULL)");
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

}
