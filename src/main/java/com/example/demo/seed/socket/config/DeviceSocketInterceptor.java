package com.example.demo.seed.socket.config;

import com.example.demo.device.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class DeviceSocketInterceptor implements HandshakeInterceptor {
    private final UserDeviceRepository userDeviceRepository;

    /**
     * 기기가 웹 소켓 handshake 시점에 전송하는 MAC 주소를 기반으로 인증
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            String MAC = Objects.requireNonNull(request.getHeaders().get("mac")).toString();
            MAC = MAC.replace("[", "");
            MAC = MAC.replace("]", "");
            boolean exists =  userDeviceRepository.existsByMac(MAC);
            if(exists) {
                log.info("device connection established from " + MAC);
                return true;
            }
            log.warn("device connection attempt from disallowed address " + MAC);
            return false;
        } catch (NullPointerException e) {
            log.warn("required header missing");
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
