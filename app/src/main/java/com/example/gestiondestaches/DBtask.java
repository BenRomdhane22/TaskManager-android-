package com.example.gestiondestaches;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBtask {
    private SQLiteOpenHelper SQLhelper;
    public DBtask(SQLiteOpenHelper helper){ this.SQLhelper=helper;}

    public void insert(Task task){
        SQLiteDatabase db = SQLhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseApplis.Task.columnTitle,task.getTitle());
        cv.put(DataBaseApplis.Task.columnDescription,task.getDescription());
        cv.put(DataBaseApplis.Task.columnDate,task.getDate());
        cv.put(DataBaseApplis.Task.columnStatus, task.getStatus());
        db.insert(DataBaseApplis.Task.tableName,null,cv);
    }

    Cursor readTasks(){
        String query ="SELECT * FROM " + DataBaseApplis.Task.tableName;
        SQLiteDatabase db = SQLhelper.getReadableDatabase();
        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Cursor cursor = readTasks();
        while (cursor.moveToNext()) {
            Task task = new Task(
                    cursor.getInt(DataBaseApplis.Task.numColId),    // Ajout de l'ID
                    cursor.getString(DataBaseApplis.Task.numColTitle),
                    cursor.getString(DataBaseApplis.Task.numColDescription),
                    cursor.getString(DataBaseApplis.Task.numColDate),
                    cursor.getString(DataBaseApplis.Task.numColStatus)
            );
            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = SQLhelper.getWritableDatabase();
        db.delete(
                DataBaseApplis.Task.tableName,
                DataBaseApplis.Task._ID + " = ?",
                new String[]{String.valueOf(task.getId())}
        );
    }

    public void updateTask(Task task) {
        SQLiteDatabase db = SQLhelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseApplis.Task.columnTitle,task.getTitle());
        cv.put(DataBaseApplis.Task.columnDescription,task.getDescription());
        cv.put(DataBaseApplis.Task.columnDate,task.getDate());
        cv.put(DataBaseApplis.Task.columnStatus, task.getStatus());

        db.update(DataBaseApplis.Task.tableName, cv, DataBaseApplis.Task._ID + " = ?", new String[]{String.valueOf(task.getId())});
    }

    public void updateTaskStatus(int taskId, String status) {
        SQLiteDatabase db = SQLhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseApplis.Task.columnStatus, status);

        db.update(
                DataBaseApplis.Task.tableName,
                values,
                DataBaseApplis.Task._ID + " = ?",
                new String[]{String.valueOf(taskId)}
        );
    }

    public Task getTaskById(int taskId) {
        SQLiteDatabase db = SQLhelper.getReadableDatabase();
        String query = "SELECT * FROM " + DataBaseApplis.Task.tableName +
                " WHERE " + DataBaseApplis.Task._ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(taskId)});

        if (cursor != null && cursor.moveToFirst()) {
            Task task = new Task(
                    cursor.getInt(DataBaseApplis.Task.numColId),
                    cursor.getString(DataBaseApplis.Task.numColTitle),
                    cursor.getString(DataBaseApplis.Task.numColDescription),
                    cursor.getString(DataBaseApplis.Task.numColDate),
                    cursor.getString(DataBaseApplis.Task.numColStatus)
            );
            cursor.close();
            return task;
        } else {
            if (cursor != null) cursor.close();
            return null;
        }
    }

    }









