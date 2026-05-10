package com.demo.demo.controller;

import com.demo.demo.model.dto.requestDto.LoginDto;
import com.demo.demo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    // Register için de DTO kullanmak en güvenli yoldur.
    // Şimdilik loginDto'yu burada da kullanabiliriz (ikisi de aynı alanları bekliyor).
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginDto dto) {
        String result = service.register(dto.getUsername(), dto.getPassword());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        // Token'ı alıyoruz
        String token = service.login(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok(token);
    }
}