package com.example.project.entity;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_MGR("ROLE_MGR"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String value(){
        return role;
    }
}
