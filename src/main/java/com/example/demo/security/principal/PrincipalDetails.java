package com.example.demo.security.principal;

import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.user.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails {
    private User user;

    private Map<String, Object> userInformation;

    public PrincipalDetails(User user)
    {
        this.user = user;
    }

    public PrincipalDetails(Map<String, Object> userInformation) {
        this.userInformation = userInformation;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if(user != null) {
            authorities.add(()-> user.getRole().toString());
        }
        if(userInformation != null) {
            String role = userInformation.get(JwtUtil.claimUserRole).toString();
            authorities.add(()->role);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        if(user == null) {
            return "";
        }
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (user == null) {
            return "";
        }
        return user.getUsername();
    }

    public String getRole() {
        if (user == null) {
            return "";
        }
        return user.getRole().toString();
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
}
