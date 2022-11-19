package com.example.demo.services;

import com.example.demo.models.RefreshToken;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.dtos.AuthDTO;
import com.example.demo.dtos.SignInResponseDTO;
import com.example.demo.exceptions.TokenRefreshException;
import com.example.demo.models.User;
import com.example.demo.converters.UserConverter;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.refresh.expires.ms}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public SignInResponseDTO signIn(AuthDTO data){
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.getUsername(), data.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(user);

        RefreshToken refreshToken  = this.createRefreshToken(user.getId());

        return new SignInResponseDTO(accessToken, refreshToken.getToken(),UserConverter.toDTO(user));
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token expired");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

}
