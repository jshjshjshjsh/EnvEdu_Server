package com.example.demo.seed.socket.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.jwt.model.JwtRefreshToken;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class SocketConnectionInterceptor implements HandshakeInterceptor {
    private final UserDeviceRepository userDeviceRepository;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            String refreshToken = URLDecoder.decode(Objects.requireNonNull(request.getHeaders().get("cookie")).get(0), StandardCharsets.UTF_8);
            JWT.require(JwtUtil.getAlgorithm()).build().verify(refreshToken.replace(JwtRefreshToken.tokenName + "=" + JwtUtil.tokenType, ""));
        } catch (NullPointerException e) {
            InetAddress ipAddress = request.getRemoteAddress().getAddress();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ipAddress);
            if (networkInterface != null) {
                byte[] macAddressBytes = networkInterface.getHardwareAddress();
                if (macAddressBytes != null) {
                    String macAddress = Arrays.toString(macAddressBytes);
                    log.info("mac address: " + macAddress);
                    userDeviceRepository.findByMac(macAddress).orElseThrow(()->new IllegalArgumentException("허용되지 않은 기기"));
                    return true;
                }
            }
            return false;
        } catch (JWTVerificationException e) {
            log.warn("not authorized");
            return false;
        }
        log.info("socket handshake completed");
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}
