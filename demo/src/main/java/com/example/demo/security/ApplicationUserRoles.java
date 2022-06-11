package com.example.demo.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import  static com.example.demo.security.ApplicationPermissions.*;


public enum ApplicationUserRoles {

    ADMIN(Sets.newHashSet(COURSE_READ,COURSE_WRITE,
                          STUDENT_READ,STUDENT_WRITE,
                          USERS_READ, USERS_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ,
                                 STUDENT_READ,
                                 USERS_READ)),
    STUDENT(Sets.newHashSet());

    private final Set<ApplicationPermissions> permissions;

    ApplicationUserRoles(Set<ApplicationPermissions> permissions){
        this.permissions=permissions;
    }

    public Set<ApplicationPermissions> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthoirities(){

        Set<SimpleGrantedAuthority> authorities=
                getPermissions().stream()
                        .map(permission-> new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toSet());
                authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authorities;
    }
}
