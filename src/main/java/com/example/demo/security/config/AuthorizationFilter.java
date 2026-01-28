package com.example.demo.security.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.cookie.util.CookieUtil;
import com.example.demo.exceptions.NoJwtTokenContainedException;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.security.principal.AuthorizationFilterPrincipalDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();

        if(cookies == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            AuthorizationFilterPrincipalDetails principalDetails = new AuthorizationFilterPrincipalDetails(cookies);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,
                    null,
                    principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Set-Cookie", CookieUtil.generateLogoutCookie().toString());
        } catch (NoJwtTokenContainedException e) {
            chain.doFilter(request, response);
        }
    }
}
