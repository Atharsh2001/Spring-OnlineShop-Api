package com.example.springonlineshop.controller;

import com.example.springonlineshop.ProductIdGenerator;
import com.example.springonlineshop.models.Retailer;
import com.example.springonlineshop.repository.RetailerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "retailer")
public class retailerController {
    @Autowired
    RetailerRepository retailRepo;
    @PostMapping("/")
    public ResponseEntity<String> addRetailer(@RequestBody Retailer newRetailer){
        List<Retailer> details = retailRepo.findAll();
        ArrayList<String> allNames = new ArrayList<>();
        for(Retailer i:details){
            allNames.add(i.getName());
        }
        if(allNames.contains(newRetailer.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Retailer Name Already Exists");
        }
        else{
            retailRepo.save(newRetailer);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Retailer Added successfully");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProducts(@PathVariable String  id){
        try{
            Optional<Retailer> productsById = retailRepo.findById(id);
            if(productsById.get().isAccess()) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(productsById.get().getProducts());
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your Account Has Been Not Yet Approved !!!");
            }
        }
        catch (NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Id Found !!!");
        }
    }

    @PostMapping("/{id}/")
    public ResponseEntity<Object> addProduct(@PathVariable String id, @RequestBody HashMap<String,Object> newProduct){
        try{
            Optional<Retailer> productsById = retailRepo.findById(id);
            if(productsById.get().isAccess()) {
                ProductIdGenerator idGen = new ProductIdGenerator();
                newProduct.put("product_id",idGen.getNewProductId());
                newProduct.put("product_reviews",null);
                if(productsById.get().getProducts()==null) {
                    ArrayList<HashMap<String, Object>> temp = new ArrayList<>();
                    temp.add(newProduct);
                    productsById.get().setProducts(temp);
                    retailRepo.save(productsById.get());
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product Added Successfully");
                }
                else{
                    if(retailRepo.getProductsNameById(id).contains(newProduct.get("product_name"))){
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Already Exists !!!");
                    }
                    else {
                        ArrayList<HashMap<String, Object>> temp = productsById.get().getProducts();
                        temp.add(newProduct);
                        productsById.get().setProducts(temp);
                        retailRepo.save(productsById.get());
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product Added Successfully");
                    }
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your Account Has Been Not Yet Approved !!!");
            }
        }
        catch (NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Id Found !!!");
        }
    }

    @DeleteMapping("/{id}/{prName}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id, @PathVariable String prName){
        try{
            Optional<Retailer> productById = retailRepo.findById(id);
            if(productById.get().isAccess()){
                if(retailRepo.getProductsNameById(id).contains(prName)) {
                    ArrayList<HashMap<String,Object>> products = productById.get().getProducts();
                    for(HashMap<String,Object> i:products){
                        if(i.get("product_name").equals(prName)){
                            products.remove(i);
                            productById.get().setProducts(products);
                            retailRepo.save(productById.get());
                            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product has been Sucessfully Deleted");
                        }
                    }
                    return null;
                }
                else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Product Not Exists !!!");
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your Account Has Been Not Yet Approved !!!");
            }
        }
        catch (NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Id Found !!!");
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody HashMap updatedProduct){
        try{
            Optional<Retailer> productById = retailRepo.findById(id);
            if(productById.get().isAccess()){
                if(retailRepo.getProductsNameById(id).contains(updatedProduct.get("product_name"))) {
                    ArrayList<HashMap<String, Object>> products = productById.get().getProducts();
                    for (HashMap<String, Object> i : products) {
                        if (i.get("product_name").equals(updatedProduct.get("product_name"))) {
                            i.putAll(updatedProduct);
                            productById.get().setProducts(products);
                            retailRepo.save(productById.get());
                            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product has been updated successfully !!!");
                        }
                    }
                }
                else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Product Not Exists !!!");
                }
            }
            else
            {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your Account Has Been Not Yet Approved !!!");
            }
        }
        catch (NoSuchElementException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Id Found !!!");
        }
        return null;
    }

}
