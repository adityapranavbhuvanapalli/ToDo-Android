package com.example.todo.model;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String name;
    private String desc;

    public Task(int id, String name, String desc) {
        this.setId(id);
        this.setName(name);
        this.setDesc(desc);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
