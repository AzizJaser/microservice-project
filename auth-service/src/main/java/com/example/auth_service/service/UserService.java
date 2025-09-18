package com.example.auth_service.service;

import com.example.auth_service.entity.User;
import com.example.auth_service.enums.Status;
import com.example.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public User createUser(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username already exists!");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email already exists!");
        }


        return userRepository.save(user);
    }


    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserById(long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " not found!"));
    }


    public User updateUser(long id, User newUser) {
        // Find existing user
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        // Check username uniqueness (exclude current user)
        Optional<User> existingUser = userRepository.findByUsername(newUser.getUsername());
        if(existingUser.isPresent() && !existingUser.get().getId().equals(id)){
            throw new IllegalArgumentException("Username already exists!");
        }

        // Check email uniqueness (exclude current user)
        Optional<User> existingEmail = userRepository.findByEmail(newUser.getEmail());
        if(existingEmail.isPresent() && !existingEmail.get().getId().equals(id)){
            throw new IllegalArgumentException("Email already exists!");
        }

        System.out.println("Password length = " + user.getPassword().length());

        // Update fields
        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setAge(newUser.getAge());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setPhoneNumber(newUser.getPhoneNumber());

        // Update password only if provided
        if(newUser.getPassword() != null && !newUser.getPassword().isEmpty()){
            user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        }

        return userRepository.save(user); // Don't forget to save!
    }

    public void deleteUser(long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user is was not found!"));
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String username){

        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("user was not found"));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("user was not found"));
    }

    public void updateUserStatus(Long id, Status status){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user not with id: "+id+" is not found!"));
        user.setStatus(status);
        userRepository.save(user);
    }
}
