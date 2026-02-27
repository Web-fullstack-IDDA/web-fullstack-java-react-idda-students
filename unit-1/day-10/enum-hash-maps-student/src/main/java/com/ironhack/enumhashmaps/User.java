package com.ironhack.enumhashmaps;

// A User model class that uses the Status enum as one of its fields.
public class User {

    private String name;
    private Status status;

    public User(String name) {
        this.name = name;
        this.status = Status.ONLINE;  // default status
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "', status=" + status + "}";
    }
}
