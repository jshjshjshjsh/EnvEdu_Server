package com.example.demo.seed.socket.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.jwt.model.JwtRefreshToken;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class SocketConnectionInterceptor implements HandshakeInterceptor {
    /**
     * 로그인 시 발급되는 쿠키를 이용한 인증
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            String refreshToken = URLDecoder.decode(Objects.requireNonNull(request.getHeaders().get("cookie")).get(0), StandardCharsets.UTF_8);
            JWT.require(JwtUtil.getAlgorithm()).build().verify(refreshToken.replace(JwtRefreshToken.tokenName + "=" + JwtUtil.tokenType, ""));
        } catch (NullPointerException e) {
            log.warn("required header missing");
            return false;
        } catch (JWTVerificationException e) {
            log.warn("invalid token");
            return false;
        }
        log.info("connection established");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}
