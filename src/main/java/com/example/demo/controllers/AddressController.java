package com.example.demo.controllers;

import com.example.demo.models.Address;
import com.example.demo.models.Car;
import com.example.demo.models.User;
import com.example.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping
    public ResponseEntity<List<Address>> list(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Address> data = addressService.list(user.getId());

        return ResponseEntity.ok(data);
    }

    @PostMapping
    public ResponseEntity<Address> save(@RequestBody Address payload){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        payload.setUser(user);

        Address data = addressService.save(payload);


        return new ResponseEntity<Address>(data,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){

        addressService.delete(id);

        return ResponseEntity.ok(null);
    }

}
