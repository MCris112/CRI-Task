package com.darkredgm.cri_task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.darkredgm.cri_task.Classes.ToDoItemClass;
import com.darkredgm.cri_task.Classes.ToDoItemFullClass;
import com.darkredgm.cri_task.db.ToDoItemsDb;

public class ToDoItemViewActivity extends AppCompatActivity {

    TextView tv_title, tv_description, tv_timestamp, btn_delete, btn_edit;
    int TODO_ITEM_ID = 0;

    ToDoItemsDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_item_view);

        db = new ToDoItemsDb(ToDoItemViewActivity.this);

        tv_title = findViewById(R.id.tv_title);
        tv_description = findViewById(R.id.tv_description);
        tv_timestamp = findViewById(R.id.tv_timestamp);

        btn_delete = findViewById(R.id.btn_delete);
        btn_edit = findViewById(R.id.btn_edit);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            TODO_ITEM_ID = extras.getInt("TODO_ITEM_ID", 0);
        }

        updateItemData();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoItemViewActivity.this, ToDoItemEditActivity.class);
                intent.putExtra("TODO_ITEM_ID", TODO_ITEM_ID);
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ToDoItemViewActivity.this);
                builder.setMessage( getString(R.string.dialog_want_to_delete) )
                        .setPositiveButton(R.string.dialog_opt_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if( db.delete(TODO_ITEM_ID) ){
                                    finish();
                                    return;
                                }

                                Toast.makeText(ToDoItemViewActivity.this, getString(R.string.msg_error_delete_item) , Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.dialog_opt_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateItemData();
    }

    private void updateItemData(){
        ToDoItemFullClass toDoItemFullClass = db.get(TODO_ITEM_ID);

        if(toDoItemFullClass == null) {
            finish();
            return;
        }

        tv_title.setText(toDoItemFullClass.getTitle());
        tv_description.setText(toDoItemFullClass.getDescription());
        tv_timestamp.setText( getString( R.string.app_item_created_at, toDoItemFullClass.getTimestamp() ));
    }
}