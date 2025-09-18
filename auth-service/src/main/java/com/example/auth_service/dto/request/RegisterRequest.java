package com.example.auth_service.dto.request;

import jakarta.validation.constraints.*;

public class RegisterRequest {


    @NotBlank(message = "username can not be null")
    @Size(min = 3,max = 10, message = "username length must be between 3 and 10 character")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    private String username;

    @NotBlank(message = "password can not be null")
    @Size(min = 3,max = 10,message = "password length must be between 3 and 10 character")
    private String password;

    @NotBlank(message = "email can not be null")
    @Email
    private String email;

    @NotBlank(message = "first name can not be null")
    @Size(min = 3, message = "first name must be more than 2 character")
    private String firstName;

    @NotBlank(message = "last name can not be null")
    @Size(min = 3,message = "last name must be more than 2 character")
    private String lastName;

    @NotNull(message = "age can not be null")
    @Min(value = 18 , message = "age must more than 17")
    @Max(value = 120, message = "Age must not exceed 120")
    private int age;

    @NotBlank
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number is invalid")
    private String phoneNumber;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password, String firstName, String lastName, int age, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
