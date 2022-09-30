package com.example.springonlineshop;
import java.util.Random;
public class ProductIdGenerator {
    public String getNewProductId() {
        Random r = new Random();
        int n = r.nextInt();
        String Hexadecimal = Integer.toHexString(n);
        return Hexadecimal;
    }
}
