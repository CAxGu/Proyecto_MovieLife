package com.herprogramacion.restaurantericoparico.models;

public class ToDoItem {
    private String item;
    private String username;
    private boolean completed;

    public ToDoItem(){ }

    public ToDoItem(String item, String username) {
        this.username = username;
        this.item = item;
        this.completed = false;
    }

    //getters & setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
