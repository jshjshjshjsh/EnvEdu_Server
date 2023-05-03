package com.example.demo.security.config;

import com.example.demo.cookie.util.CookieUtil;
import com.example.demo.jwt.model.JwtAccessToken;
import com.example.demo.jwt.model.JwtRefreshToken;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.security.principal.PrincipalDetails;
import com.example.demo.user.dto.request.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        LoginDTO loginDTO = null;

        try {
            loginDTO = om.readValue(request.getInputStream(), LoginDTO.class);
        }
        catch (Exception e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        if(loginDTO != null) {
            try {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(), loginDTO.getPassword());

                return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            }
            catch (BadCredentialsException | UsernameNotFoundException e) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        JwtRefreshToken jwtRefreshToken = JwtRefreshToken.generateJwtRefreshToken(principalDetails);
        JwtAccessToken jwtAccessToken = JwtAccessToken.generateJwtAccessToken(principalDetails);

        String jwtAccessTokenString = JwtUtil.convertJwtToString(jwtAccessToken);
        ResponseCookie cookie = CookieUtil.generateCookieForRefreshToken(jwtRefreshToken);

        response.setHeader("Set-Cookie", cookie.toString());
        response.setHeader(JwtUtil.headerString, jwtAccessTokenString);
    }
}
