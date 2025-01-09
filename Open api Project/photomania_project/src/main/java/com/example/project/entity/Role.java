package com.example.project.entity;

public enum Role {
    ROLE_USER("ROLE_USER"),//유저 권한
    ROLE_ADMIN("ROLE_ADMIN");//어드민 권한

    private String role;

    Role(String role){
        this.role = role;
    }
    public String value(){
        return role;
    }
}
