package com.example.demo.services;

import com.example.demo.configs.WebSecurity;
import com.example.demo.models.User;
import com.example.demo.dtos.SaveUserDTO;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService  {

    @Autowired
    private UserRepository repository;

    @Autowired
    WebSecurity security;

    public User save(SaveUserDTO data){
        log.debug("UserService method: {} in: {}","save",data);

        User user = new User();

        user.setEmail(data.getEmail());
        user.setPassword(security.passwordEncoder().encode(data.getPassword()));

        user = this.repository.save(user);

        return user;

    }

    public Page<User> paginate(Pageable pageable){
        Page<User> paginated = this.repository.findAll(pageable);

        return paginated;
    }


}
