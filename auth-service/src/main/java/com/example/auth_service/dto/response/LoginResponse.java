package com.example.auth_service.dto.response;

import java.time.LocalDateTime;

public class LoginResponse {

    private String token;

    private final String type = "Bearer ";

    private LocalDateTime issueAt;

    private LocalDateTime expireAt;

    public LoginResponse() {
    }

    public LoginResponse(String token, LocalDateTime expireAt, LocalDateTime issueAt) {
        this.token = token;
        this.issueAt = issueAt;
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setIssueAt(LocalDateTime issueAt) {
        this.issueAt = issueAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public LocalDateTime getIssueAt() {
        return issueAt;
    }

}
