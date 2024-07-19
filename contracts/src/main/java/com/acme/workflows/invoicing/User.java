package com.acme.workflows.invoicing;

public class User  {

    private String id;

    public User(String id) {
        this.id = id;
    }

    // Needed for Json serialization
    public User() { super(); }


    public String getId() {
        return id;
    }
}
