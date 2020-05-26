package com.echo.moviememoir.entity;

public class Credential {
    private Integer credentialsId;
    private String username;
    private String password;
    private User userId;

    public Credential() {
    }

    public Credential(Integer credentialsId, String username, String password, User userId) {
        this.credentialsId = credentialsId;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public Integer getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Integer credentialsId) {
        this.credentialsId = credentialsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "credentialsId=" + credentialsId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userId=" + userId +
                '}';
    }
}
