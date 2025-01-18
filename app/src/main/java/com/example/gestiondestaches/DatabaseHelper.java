package com.example.gestiondestaches;

import static android.app.DownloadManager.COLUMN_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDB.db";
    private static final int DATABASE_VERSION = 2;
    private static final  String CREATE_TASKS_TABLE = "CREATE TABLE " + DataBaseApplis.Task.tableName + " (" +
            DataBaseApplis.Task._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DataBaseApplis.Task.columnTitle + " TEXT NOT NULL, " +
            DataBaseApplis.Task.columnDescription + " TEXT, " +
            DataBaseApplis.Task.columnDate + " TEXT, " +
            DataBaseApplis.Task.columnStatus + " TEXT DEFAULT 'incomplet'" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASKS_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseApplis.Task.tableName);
        onCreate(db);
    }





}
