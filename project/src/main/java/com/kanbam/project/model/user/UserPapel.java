package com.kanbam.project.model.user;

public enum UserPapel {
    ADMIN("admin"),
    USER("user");

    private String papel;

    UserRole(String papel){
        this.papel = papel;
    }

    public String getPapel(){
        return papel;
    }
}