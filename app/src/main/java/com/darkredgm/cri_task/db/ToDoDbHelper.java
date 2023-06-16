package com.darkredgm.cri_task.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ToDoDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";
    public static final String TABLE_TODO_ITEMS = "t_todo_items";

    public static final String COLUMN_ID = "t_id";
    public static final String COLUMN_TITLE = "t_title";
    public static final String COLUMN_DESCRIPTION = "t_description";
    public static final String COLUMN_DID_IT = "t_did_it";
    public static final String COLUMN_TIMESTAMP = "t_timestamp";

    public ToDoDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TODO_ITEMS + " ("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_TITLE + " TEXT NOT NULL,"+
                COLUMN_DESCRIPTION + " TEXT,"+
                COLUMN_DID_IT+ " BOOLEAN NOT NULL DEFAULT 0 CHECK("+COLUMN_DID_IT+" IN (0,1)),"+
                COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+ TABLE_TODO_ITEMS);
        onCreate(sqLiteDatabase);
    }
}
