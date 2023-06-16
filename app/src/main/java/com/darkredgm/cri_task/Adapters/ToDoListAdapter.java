package com.darkredgm.cri_task.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.darkredgm.cri_task.Classes.ToDoItemClass;
import com.darkredgm.cri_task.R;
import com.darkredgm.cri_task.ToDoItemViewActivity;
import com.darkredgm.cri_task.db.ToDoItemsDb;

import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private ArrayList<ToDoItemClass> toDoItemsList;
    private Context context;

    public ToDoListAdapter(ArrayList<ToDoItemClass> toDoItemsList, Context context) {
        this.toDoItemsList = toDoItemsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListAdapter.ViewHolder holder, int position) {
        holder.title_tv.setText(toDoItemsList.get(position).getTitle());

        final int itemPosition = position;

        ToDoItemClass itemClass = toDoItemsList.get(position);

        holder.btn_todo_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ToDoItemViewActivity.class);
                intent.putExtra("TODO_ITEM_ID", toDoItemsList.get(itemPosition).getId());
                context.startActivity(intent);
            }
        });

        holder.btn_cb.setChecked(toDoItemsList.get(position).getIs_did());

        holder.btn_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                ToDoItemsDb db = new ToDoItemsDb(context);
                
                if(db.setDid(itemClass.getId(), b)){
                    Toast.makeText(context, context.getString(R.string.msg_item_save_ok), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(context, context.getString(R.string.msg_item_save_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.toDoItemsList.size();
    }

    public void updateItems(ArrayList<ToDoItemClass> toDoItems) {
        this.toDoItemsList = toDoItems;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox btn_cb;
        TextView title_tv;
        LinearLayout btn_todo_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_cb = (CheckBox) itemView.findViewById(R.id.btn_cb);
            title_tv = (TextView) itemView.findViewById(R.id.title_tv);
            btn_todo_item = (LinearLayout) itemView.findViewById(R.id.btn_todo_item);
        }
    }
}
