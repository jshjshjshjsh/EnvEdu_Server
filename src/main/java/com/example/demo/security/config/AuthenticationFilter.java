package com.example.demo.security.config;

import com.example.demo.cookie.util.CookieUtil;
import com.example.demo.jwt.model.JwtAccessToken;
import com.example.demo.jwt.model.JwtRefreshToken;
import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.security.principal.AuthenticationFilterPrincipalDetails;
import com.example.demo.user.dto.request.LoginDTO;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.State;
import com.example.demo.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

/**
 * /login url로 request를 보냈을 때 거치는 필터
 */
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
//    private final UserRepository userRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        LoginDTO loginDTO = null;

        try {
            loginDTO = om.readValue(request.getInputStream(), LoginDTO.class);

            log.info("requestPath : " + request.getRequestURI());
            log.info("loginDTO : " + loginDTO);

        }
        catch (Exception e) {
            log.info("로그인 쪽 이건가");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }

        if(loginDTO != null) {
            try {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(), loginDTO.getPassword());

//                Optional<User> user = userRepository.findByUsernameAndState(loginDTO.getUsername(), State.ACTIVE);

                log.info("확인 : " + usernamePasswordAuthenticationToken.getPrincipal());

                return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            }
            catch (BadCredentialsException | UsernameNotFoundException e) {
                /**
                 * todo: 여기서 status가 400으로 설정됐을 때 프론트에서 에러 메세지 지정, axios interceptor에서 에러를 처리하는 부분은 ResponseEntity에서 리턴된 status를 기반으로 처리함
                 */
                log.info("여긴가?");
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        AuthenticationFilterPrincipalDetails principalDetails = (AuthenticationFilterPrincipalDetails) authResult.getPrincipal();

        JwtRefreshToken jwtRefreshToken = JwtRefreshToken.generateJwtRefreshToken(principalDetails);
        JwtAccessToken jwtAccessToken = JwtAccessToken.generateJwtAccessToken(principalDetails);

        String jwtAccessTokenString = JwtUtil.convertJwtToString(jwtAccessToken);
        ResponseCookie cookie = CookieUtil.generateCookieForRefreshToken(jwtRefreshToken);

        response.setHeader("Set-Cookie", cookie.toString());
        response.setHeader(JwtUtil.headerString, jwtAccessTokenString);
    }
}
