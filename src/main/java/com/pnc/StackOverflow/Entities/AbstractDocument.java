package com.pnc.StackOverflow.Entities;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public class AbstractDocument {
    @Id
    public String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
