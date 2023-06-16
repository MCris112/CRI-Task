package com.darkredgm.cri_task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darkredgm.cri_task.db.ToDoItemsDb;

public class ToDoItemAddActivity extends AppCompatActivity {

    EditText et_title, et_description;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_item_add);

        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);

        btn_add = findViewById(R.id.btn_add_item);

        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ToDoItemsDb toDoItemsDb = new ToDoItemsDb(ToDoItemAddActivity.this);

                String title = et_title.getText().toString();
                String description = et_description.getText().toString();

                if(!title.matches("")){
                    if(toDoItemsDb.insert(title, description) != 0){
                        finish();
                        return;
                    }

                    Toast.makeText(ToDoItemAddActivity.this, R.string.msg_db_error_insert, Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(ToDoItemAddActivity.this, R.string.msg_fields_empty, Toast.LENGTH_SHORT).show();
            }
        });
    }
}