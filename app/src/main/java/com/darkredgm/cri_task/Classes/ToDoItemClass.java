package com.darkredgm.cri_task.Classes;

public class ToDoItemClass {
    private Integer id;
    private String title;

    public Boolean getIs_did() {
        return is_did;
    }

    public void setIs_did(Boolean is_did) {
        this.is_did = is_did;
    }

    private  Boolean is_did;

    public ToDoItemClass(Integer id, String title, Boolean is_did) {
        this.id = id;
        this.title = title;
        this.is_did = is_did;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
