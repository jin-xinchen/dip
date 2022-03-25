package com.jin.tool.json.gson;

public class Department {
    private long id;
    private String name;
    private User[] users;

    public Department(long id, String name, User[] users) {
        this.id = id;
        this.name = name;
        users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        users = users;
    }
}
