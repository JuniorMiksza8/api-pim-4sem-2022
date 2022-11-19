package com.example.demo.controllers;

import com.example.demo.models.RefreshToken;
import com.example.demo.dtos.RefreshTokenResponse;
import com.example.demo.dtos.SignInResponseDTO;
import com.example.demo.dtos.AuthDTO;
import com.example.demo.exceptions.TokenRefreshException;
import com.example.demo.services.AuthService;
import com.example.demo.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthService authService;

    @Validated
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDTO> authenticate(@RequestBody AuthDTO payload){
        SignInResponseDTO data = authService.signIn(payload);

        return ResponseEntity.ok().body(data);
    }

    @PostMapping("/refresh/{token}")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@PathVariable("token") String token) {

        Optional<RefreshToken> refreshed =  authService.findByToken(token)
                .map(authService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    return authService.createRefreshToken(user.getId());
                });

        if(refreshed.isEmpty()) throw new TokenRefreshException(token, "Refresh token not found");

        RefreshTokenResponse data = new RefreshTokenResponse(
                refreshed.get().getToken(),
                jwtUtil.generateAccessToken(refreshed.get().getUser())
        );

        return ResponseEntity.ok(data) ;
    }

}
