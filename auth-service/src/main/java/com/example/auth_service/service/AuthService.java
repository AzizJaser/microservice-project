package com.example.auth_service.service;

import com.example.auth_service.dto.request.LoginRequest;
import com.example.auth_service.dto.request.RegisterRequest;
import com.example.auth_service.dto.response.LoginResponse;
import com.example.auth_service.entity.User;
import com.example.auth_service.enums.Roles;
import com.example.auth_service.enums.Status;
import com.example.auth_service.security.Jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;


    public LoginResponse register(RegisterRequest request){
        if(userService.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("Email is already used !");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        Set<Roles> defaultRoles = new HashSet<>();
        defaultRoles.add(Roles.ROLE_USER);  // assuming this is one of your enum values
        user.setRoles(defaultRoles);
        user.setStatus(Status.ACTIVE);
        userService.createUser(user);
        String token = jwtService.generateToken(user);
        LoginResponse response = new LoginResponse(token, jwtService.extractExpiration(token), LocalDateTime.now());
        return response;
    }

    public LoginResponse login(LoginRequest request){
        User user = userService.getUserByEmail(request.getEmail());


        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials!");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token, jwtService.extractExpiration(token), LocalDateTime.now());
    }

}
