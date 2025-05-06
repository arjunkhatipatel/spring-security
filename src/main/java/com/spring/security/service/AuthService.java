package com.spring.security.service;

import com.spring.security.Util.JwtUtil;
import com.spring.security.model.Users;
import com.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public String login(Users user) {
        Optional<Users> us = userRepository.findByUsername(user.getUsername());

        if (us.isPresent()) {
            if (passwordEncoder.matches(user.getPassword(), us.get().getPassword())) {
                return jwtUtil.generateToken(user.getUsername());
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    public Users signup(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
