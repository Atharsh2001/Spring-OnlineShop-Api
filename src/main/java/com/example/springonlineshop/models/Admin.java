package com.example.springonlineshop.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Document
public class Admin {
    @Id
    private String name;
    @Field
    private ArrayList<String> catogories;
    public Admin(){}
    public Admin(String name,ArrayList<String> catogories) {
        this.name=name;
        this.catogories = catogories;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<String> getCatogories() {
        return catogories;
    }
    public void setCatogories(ArrayList<String> catogories) {
        this.catogories = catogories;
    }
}
