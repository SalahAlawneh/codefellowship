package com.salahcodefellowship.codefellowship.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ApplicationUser implements UserDetails {

   DBUser dbUser;

    public ApplicationUser(DBUser dbUser){
        this.dbUser = dbUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(dbUser.getAuthority());
        List<SimpleGrantedAuthority> userAutjorities = new ArrayList<SimpleGrantedAuthority>();
        userAutjorities.add(simpleGrantedAuthority);
        return userAutjorities;

    }

    public Integer getId() {
        return dbUser.getId();
    }

    @Override
    public String getPassword() {
        return dbUser.getPassword();
    }

    @Override
    public String getUsername() {
        return dbUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "this is user "+ dbUser.getUsername();
    }
}
