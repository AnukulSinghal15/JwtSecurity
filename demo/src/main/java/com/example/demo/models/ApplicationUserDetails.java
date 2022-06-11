package com.example.demo.models;

import com.example.demo.entity.ApplicationUser;
import com.example.demo.security.ApplicationUserRoles;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplicationUserDetails implements UserDetails {

    private ApplicationUser user;

    private String username;
    private String password;

    private Integer isAccountNonExpired;
    private Integer isAccountNonLocked;
    private Integer isCredentialsNonExpired;
    private Integer isEnabled;

    public ApplicationUserDetails(ApplicationUser user){
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities= ApplicationUserRoles.valueOf(user.getRole()).getGrantedAuthoirities();
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
       return (user.getIsAccountNonExpired()==1) ? true : false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return (user.getIsAccountNonLocked()==1) ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return (user.getIsCredentialsNonExpired()==1) ? true : false;
    }

    @Override
    public boolean isEnabled() {
        return (user.getIsEnabled()==1) ? true : false;
    }
}
