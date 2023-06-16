package com.darkredgm.cri_task.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.darkredgm.cri_task.Classes.ToDoItemClass;
import com.darkredgm.cri_task.Classes.ToDoItemFullClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ToDoItemsDb extends ToDoDbHelper{

    Context context;

    public ToDoItemsDb(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public ToDoItemFullClass get(int id) {
        ToDoItemFullClass toDoItemFullClass = null;

        ToDoDbHelper toDoDbHelper = new ToDoDbHelper(context);
        SQLiteDatabase db = toDoDbHelper.getWritableDatabase();

        Cursor cursorItem =  null;

        cursorItem = db.rawQuery("SELECT * FROM " + TABLE_TODO_ITEMS + " WHERE " + COLUMN_ID + " = " + id, null);

        if(cursorItem.moveToFirst()){
            do{
                toDoItemFullClass = new ToDoItemFullClass( cursorItem.getInt(0), cursorItem.getString(1), cursorItem.getString(2), cursorItem.getInt(3) != 0, cursorItem.getString(4));
            } while(cursorItem.moveToNext());
        }

        cursorItem.close();

        return toDoItemFullClass;
    }

    public long insert(String title, String description){
        long id = 0;
        try{
            ToDoDbHelper toDoDbHelper = new ToDoDbHelper(context);
            SQLiteDatabase db = toDoDbHelper.getWritableDatabase();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date());

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_DESCRIPTION, description);
            values.put(COLUMN_TIMESTAMP, currentTimeStamp);

            id = db.insert(TABLE_TODO_ITEMS, null, values);
        }catch (Exception e){
            Log.e("ERROR_DB", e.getMessage());
        }

        return id;
    }

    public ArrayList<ToDoItemClass> getAll() {
        ArrayList<ToDoItemClass> items = new ArrayList<>();

        ToDoDbHelper toDoDbHelper = new ToDoDbHelper(context);
        SQLiteDatabase db = toDoDbHelper.getWritableDatabase();

        Cursor cursorItems =  null;

        cursorItems = db.rawQuery("SELECT * FROM " + TABLE_TODO_ITEMS, null);

        if(cursorItems.moveToFirst()){
            do{
                ToDoItemClass item = new ToDoItemClass( cursorItems.getInt(0), cursorItems.getString(1), cursorItems.getInt(3) != 0);
                items.add(item);
            } while(cursorItems.moveToNext());
        }

        cursorItems.close();

        return items;
    }

    public boolean edit(int id, String title, String description){
        boolean result = false;
        ToDoDbHelper toDoDbHelper = new ToDoDbHelper(context);
        SQLiteDatabase db = toDoDbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_TODO_ITEMS + " SET "+COLUMN_TITLE+" = '" + title + "', "+COLUMN_DESCRIPTION+" = '" + description + "' WHERE "+COLUMN_ID+" = '" + id + "' ");
            result = true;
        } catch (Exception e) {
            Log.e("TODO_ITEM_DB", e.getMessage());
        } finally {
            db.close();
        }

        return result;
    }

    public boolean delete(int TODO_ITEM_ID) {

        boolean result = false;
        ToDoDbHelper toDoDbHelper = new ToDoDbHelper(context);
        SQLiteDatabase db = toDoDbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_TODO_ITEMS + " WHERE "+COLUMN_ID+" = '" + TODO_ITEM_ID + "'");
            result = true;
        } catch (Exception e) {
            Log.e("TODO_ITEM_DB", e.getMessage());
        } finally {
            db.close();
        }

        return result;
    }

    public boolean setDid(int id, boolean is_did){
        boolean result = false;
        ToDoDbHelper toDoDbHelper = new ToDoDbHelper(context);
        SQLiteDatabase db = toDoDbHelper.getWritableDatabase();

        int isDidIt = 0;

        if(is_did) isDidIt = 1;

        Log.e("SQL", "UPDATE " + TABLE_TODO_ITEMS + " SET "+COLUMN_DID_IT+" = " + isDidIt + " WHERE "+COLUMN_ID+" = '" + id + "'");

        try {
            db.execSQL("UPDATE " + TABLE_TODO_ITEMS + " SET "+COLUMN_DID_IT+" = '" + isDidIt + "' WHERE "+COLUMN_ID+" = '" + id + "'");
            result = true;
        } catch (Exception e) {
            Log.e("TODO_ITEM_DB", e.getMessage());
        } finally {
            db.close();
        }

        return result;
    }
}
