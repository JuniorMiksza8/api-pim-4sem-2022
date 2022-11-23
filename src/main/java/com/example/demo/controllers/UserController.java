package com.example.demo.controllers;

import com.example.demo.dtos.UpdateSocialDTO;
import com.example.demo.dtos.UpdateUserDTO;
import com.example.demo.models.User;
import com.example.demo.converters.UserConverter;
import com.example.demo.services.UserService;
import com.example.demo.dtos.SaveUserDTO;
import com.example.demo.dtos.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public Page<UserDTO> listUsers(Pageable pageable){
        Page<User> users = this.userService.paginate(pageable);

        return UserConverter.toPageVO(users);
    }

    @GetMapping("/detail")
    public ResponseEntity detailUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        Optional<User> data = this.userService.findByEmail(user.getEmail());

        if(data.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(UserConverter.toDTO(data.get()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody SaveUserDTO payload){
        User user = userService.save(payload);

        return new ResponseEntity<UserDTO>(UserConverter.toDTO(user),HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UpdateUserDTO payload){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authenticated = (User) auth.getPrincipal();


        User user = userService.update(authenticated.getId(),payload);

        return new ResponseEntity<UserDTO>(UserConverter.toDTO(user),HttpStatus.CREATED);
    }

    @PutMapping("/socials")
    public ResponseEntity<UserDTO> updateSocial(@RequestBody UpdateSocialDTO payload){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();


        Optional<User> data = userService.updateSocial(user.getId(),payload);

        if(data.isEmpty()) return ResponseEntity.notFound().build();

        return new ResponseEntity<UserDTO>(UserConverter.toDTO(data.get()),HttpStatus.CREATED);
    }
}
