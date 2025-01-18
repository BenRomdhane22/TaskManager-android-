package com.example.gestiondestaches;

import android.provider.BaseColumns;

public class DataBaseApplis {
    public interface Task extends BaseColumns{
         String tableName = "task";
         String columnTitle = "title";
         String columnDescription = "description";
         String columnDate = "date";
         String columnStatus = "status";
         int numColId = 0;
         int numColTitle = 1;
         int numColDescription= 2;
         int numColDate = 3;
         int numColStatus = 4;
    }
}
