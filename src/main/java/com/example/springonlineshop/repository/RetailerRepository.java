package com.example.springonlineshop.repository;
import com.example.springonlineshop.models.Retailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public interface RetailerRepository extends MongoRepository<Retailer,String> {
   public default List<String> getRetailersByAccess(){
      List<Retailer> details = findAll();
      List<String> accessRetailer = new ArrayList<>();
      for(Retailer  i:details){
         if(i.isAccess()){
            accessRetailer.add(i.toString());
         }
      }
      return accessRetailer;
   }
   public default List<String> getRetailersByUnAccess(){
      List<Retailer> details = findAll();
      List<String> unAccessRetailer = new ArrayList<>();
      for(Retailer  i:details){
         if(i.isAccess()==false){
            unAccessRetailer.add(i.toString());
         }
      }
      return unAccessRetailer;
   }
   public default List<String> getProductsNameById(String id){
      Optional<Retailer> details = findById(id);
      List<String> allProNames = new ArrayList<>();
      ArrayList<HashMap<String, Object>> allProducts = details.get().getProducts();
      for(HashMap i:allProducts){
         allProNames.add((String) i.get("product_name"));
      }
      return allProNames;
   }
}