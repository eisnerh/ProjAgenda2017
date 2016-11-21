package com.so2.ace.agenda2017.db;

import android.provider.BaseColumns;

// Hecho por Eisner López Acevedo

public class TaskContract {
    //The TaskContract class defines constants which used to access the data in the database


    public static final String DB_NAME = "com.aziflaj.todolist.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
    }
}

