package com.example.springonlineshop.repository;
import com.example.springonlineshop.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AdminRepository extends MongoRepository<Admin,String> {
    public default Object getCatogories(){
        List<Admin> details = findAll();
        for(Admin i:details){
            if(i.getName().equals("admin")){
                return i.getCatogories();
            }
        }
        return "Nothing Found";
    }
}
