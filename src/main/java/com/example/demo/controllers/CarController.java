package com.example.demo.controllers;

import com.example.demo.models.Car;
import com.example.demo.models.User;
import com.example.demo.repository.CarRepository;
import com.example.demo.services.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService service;

    @GetMapping
    public ResponseEntity<List<Car>> list(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Car> data = service.list(user.getId());

        return ResponseEntity.ok(data);
    }

    @PostMapping
    public ResponseEntity<Car> save(@RequestBody Car payload){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        payload.setUser(user);

        Car data = service.save(payload);

        return ResponseEntity.created(null).body(data);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){

        service.delete(id);

        return ResponseEntity.ok().body(null);

    }

}
