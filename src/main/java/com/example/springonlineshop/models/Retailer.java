package com.example.springonlineshop.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashMap;

@Document
public class Retailer {
    @Id
    private String id;
    @Field
    private String name;
    @Field
    private ArrayList<HashMap<String,Object>> products;
    @Field
    private boolean access;

    public Retailer(String name) {
        this.name = name;
        this.products = null;
        this.access=false;
    }
    public Retailer(){}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<HashMap<String,Object>> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<HashMap<String,Object>> products) {
        this.products = products;
    }
    public boolean isAccess() {
        return access;
    }
    public void setAccess(boolean access) {
        this.access = access;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return  " Id = "+id+"    Name = "+name;
    }
}
