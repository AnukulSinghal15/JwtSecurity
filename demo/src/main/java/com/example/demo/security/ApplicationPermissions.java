package com.example.demo.security;

public enum ApplicationPermissions {

    USERS_WRITE("users:write"),
    USERS_READ("users:read"),
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;

    ApplicationPermissions(String permission){
        this.permission= permission;
    }

    public String getPermission(){
        return permission;
    }

}
