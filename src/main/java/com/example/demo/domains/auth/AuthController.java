package com.example.demo.domains.auth;

import com.example.demo.domains.user.User;
import com.example.demo.domains.auth.dtos.AuthDTO;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/sign-in")
    public ResponseEntity<String> authenticate(@RequestBody AuthDTO data){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getUsername(), data.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(user);

        return ResponseEntity.ok().body(accessToken);
    }

}
