package com.example.springonlineshop.controller;
import com.example.springonlineshop.models.*;
import com.example.springonlineshop.repository.AdminRepository;
import com.example.springonlineshop.repository.RetailerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value="/admin")
public class adminController {

    @Autowired
    AdminRepository adminRepo;
    @Autowired
    RetailerRepository retailRepo;
    private boolean adminSession = false;
    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody HashMap loginDetails){
        if(loginDetails.get("login").equals("admin") && loginDetails.get("password").equals("1234")){
            adminSession=true;
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Admin Login Successfull");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin Login Credentials are invalid");
        }
    }
    @PutMapping("/")
    public ResponseEntity<String> addCatogories(@RequestBody Admin nAdmin){
        if(adminSession==true) {
            if(nAdmin.getName().equals("admin")){
                Admin ob1 = new Admin(nAdmin.getName(), nAdmin.getCatogories());
                adminRepo.save(ob1);
                return ResponseEntity.status(HttpStatus.OK).body("Catagories has been Updated");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invaild username");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login as Admin");
        }
    }
    @GetMapping("/")
    public ResponseEntity<Object> listCatagories(){
        if(adminSession==true) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminRepo.getCatogories());
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login as Admin");
        }
    }
    @GetMapping("/retailers")
    public ResponseEntity<Object> getRetailers(){
        if(adminSession==true) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(retailRepo.getRetailersByAccess());
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login as Admin");
        }
    }

    @GetMapping("/retailers/unauthorised")
    public ResponseEntity<Object> getUnaccessedRetailer(){
        if(adminSession==true) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(retailRepo.getRetailersByUnAccess());
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login as Admin");
        }
    }
    @PostMapping("/retailers")
    public ResponseEntity<Object> verifyRetailers(@RequestBody HashMap<String, ArrayList> unAcessList){
        if(adminSession==true) {
            ArrayList<String> verifyList = unAcessList.get("id");
            HashMap<String,ArrayList> noIdFound = new HashMap<>();
            noIdFound.put("No-Id-Matched",null);
            for(String i:verifyList) {
                try {
                    Optional<Retailer> singleRetailer = retailRepo.findById(i);
                    singleRetailer.get().setAccess(true);
                    retailRepo.save(singleRetailer.get());
                }
                catch (NoSuchElementException exception){
                    if(noIdFound.get("No-Id-Matched")==null){
                        ArrayList<String> temp = new ArrayList<>();
                        temp.add(i);
                        noIdFound.put("No-Id-Matched",temp);
                    }
                    else {
                        ArrayList<String> temp = noIdFound.get("No-Id-Matched");
                        temp.add(i);
                        noIdFound.put("No-Id-Matched", temp);
                    }
                }
            }
            if(noIdFound.get("No-Id-Matched")==null){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Retailers Aproved Successfully");
            }
            else{
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(noIdFound);
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login as Admin");
        }
    }
}