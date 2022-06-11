package com.example.demo.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="users")
@Getter
@ToString
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String role;

    private Integer isAccountNonExpired=1;

    private Integer isAccountNonLocked=1;

    private Integer isCredentialsNonExpired=1;

    private Integer isEnabled=1;
}
