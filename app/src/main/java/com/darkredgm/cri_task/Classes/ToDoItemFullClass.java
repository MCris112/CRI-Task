package com.darkredgm.cri_task.Classes;

public class ToDoItemFullClass extends  ToDoItemClass{

    private String description;
    private String timestamp;

    public ToDoItemFullClass(Integer id, String title, String description, Boolean is_did, String timestamp) {
        super(id, title, is_did);
        this.description = description;
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
