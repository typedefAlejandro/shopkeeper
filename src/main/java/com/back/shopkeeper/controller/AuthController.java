package com.back.shopkeeper.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.shopkeeper.model.dto.LoginDTO;
import com.back.shopkeeper.model.dto.RegisterDTO;
import com.back.shopkeeper.model.entity.User;
import com.back.shopkeeper.repository.UserRepository;
import com.back.shopkeeper.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        authService.register(dto);
        return ResponseEntity.ok(Map.of("message", "Usuário "+ dto.getName() +" registrado com sucesso!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        String token = authService.login(login.getEmail(), login.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/check-session")
    public ResponseEntity<?> checkSession(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Header de autorização ausente ou mal formatado"));
        }
        String token = authorizationHeader.substring(7);
        try {
            String email = authService.getEmailFromToken(token);
            User user = userRepository.findByEmail(email).orElseThrow(() -> 
                new RuntimeException("Usuário do token não encontrado no banco de dados")
            );
            Long userId = user.getId();
            return ResponseEntity.ok(Map.of("userId", userId));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Sessão inválida ou expirada"));
        }
    }
}
