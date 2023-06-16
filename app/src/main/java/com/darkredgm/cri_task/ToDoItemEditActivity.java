package com.darkredgm.cri_task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darkredgm.cri_task.Classes.ToDoItemFullClass;
import com.darkredgm.cri_task.db.ToDoItemsDb;

public class ToDoItemEditActivity extends AppCompatActivity {

    EditText et_title, et_description;
    Button btn_edit;
    int TODO_ITEM_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_item_edit);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            TODO_ITEM_ID = extras.getInt("TODO_ITEM_ID", 0);
        }

        ToDoItemsDb db = new ToDoItemsDb(this);
        ToDoItemFullClass toDoItemFullClass = db.get(TODO_ITEM_ID);

        if(toDoItemFullClass == null) {
            finish();
            return;
        }

        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        btn_edit = findViewById(R.id.btn_edit_item);

        et_title.setText(toDoItemFullClass.getTitle());
        et_description.setText(toDoItemFullClass.getDescription());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = et_title.getText().toString();
                String description = et_description.getText().toString();

                if(!title.matches("")){

                    ToDoItemsDb db = new ToDoItemsDb(ToDoItemEditActivity.this);

                    if(db.edit(TODO_ITEM_ID, title, description)){
                        finish();
                        return;
                    }

                    Toast.makeText(ToDoItemEditActivity.this, R.string.msg_db_error_insert, Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(ToDoItemEditActivity.this, R.string.msg_fields_empty, Toast.LENGTH_SHORT).show();
            }
        });
    }
}