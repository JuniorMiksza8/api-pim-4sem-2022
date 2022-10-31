package com.example.demo.domains.user;

import com.example.demo.domains.user.dtos.SaveUserDTO;
import com.example.demo.domains.user.dtos.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody SaveUserDTO payload){

        log.debug("Payload {}",payload);

        User user = userService.save(payload);

        return new ResponseEntity<UserDTO>(UserConverter.toDTO(user),HttpStatus.CREATED);
    }

}
