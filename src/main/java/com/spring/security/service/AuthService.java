package com.spring.security.service;

import com.spring.security.Util.JwtUtil;
import com.spring.security.model.Users;
import com.spring.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            Users dbUser = us.get();
            if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
                UserDetails userDetails = new User(
                        dbUser.getUsername(),
                        dbUser.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(dbUser.getRole()))
                );

                return jwtUtil.generateToken(userDetails);
            }
        }
        return null;
    }

    public String signup(Users user) {
        if (user.getRole().equals("ROLE_USER") || user.getRole().equals("ROLE_ADMIN")){
            return "Please give role as ROLE_USER or ROLE_ADMIN";
        }
        Optional<Users> us = userRepository.findByUsername(user.getUsername());
        if (us.isEmpty()){
            return "User already exist";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Created Successfully";
    }
}
