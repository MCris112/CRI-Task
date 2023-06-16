package com.darkredgm.cri_task;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.darkredgm.cri_task.Adapters.ToDoListAdapter;
import com.darkredgm.cri_task.Classes.ToDoItemClass;
import com.darkredgm.cri_task.db.ToDoDbHelper;
import com.darkredgm.cri_task.db.ToDoItemsDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    RecyclerView toDoList;
    FloatingActionButton todo_item_add_fb;
    ToDoListAdapter toDoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDoList = findViewById(R.id.toDoListRV);
        todo_item_add_fb = findViewById(R.id.todo_item_add_fb);

        ArrayList<ToDoItemClass> toDoItemClasses = getToDoItems();

        LinearLayoutManager lm = new LinearLayoutManager(this);
        toDoList.setLayoutManager(lm);
        toDoListAdapter = new ToDoListAdapter(toDoItemClasses, MainActivity.this);
        toDoList.setAdapter(toDoListAdapter);

        todo_item_add_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ToDoItemAddActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<ToDoItemClass> getToDoItems() {
        ToDoItemsDb toDoItemsDb = new ToDoItemsDb(MainActivity.this);

        return toDoItemsDb.getAll();
    }

    @Override
    protected void onResume() {
        super.onResume();

        toDoListAdapter.updateItems(getToDoItems());
    }
}