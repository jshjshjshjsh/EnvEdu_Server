package com.example.demo.security.principal;

import com.example.demo.jwt.util.JwtUtil;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class AuthorizationFilterPrincipalDetails implements PrincipalDetails {

    private final Map<String, Object> userInformation;

    public AuthorizationFilterPrincipalDetails(Cookie[] cookies) {
        userInformation = JwtUtil.getJwtRefreshTokenFromCookieAndParse(cookies).get(JwtUtil.claimName).asMap();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        String role = userInformation.get(JwtUtil.claimUserRole).toString();
        authorities.add(()->role);

        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public String getRole() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
