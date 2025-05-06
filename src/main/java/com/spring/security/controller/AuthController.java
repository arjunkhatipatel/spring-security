package com.spring.security.controller;

import com.spring.security.model.Users;
import com.spring.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Users user) {
        return ResponseEntity.accepted().body(authService.login(user));
    }

    @PostMapping("signup")
    public ResponseEntity<Users> signup(@RequestBody Users user) {
        return ResponseEntity.accepted().body(authService.signup(user));
    }
}
