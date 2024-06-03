package com.example.demo.seed.socket.interceptor;

import com.example.demo.device.model.UserDevice;
import com.example.demo.device.repository.UserDeviceRepository;
//import com.example.demo.seed.socket.service.WebSocketSessionService;
import com.example.demo.device.service.UserDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
public class DeviceSocketInterceptor implements HandshakeInterceptor {
    private final UserDeviceService userDeviceService;

    /**
     * 기기가 웹 소켓 handshake 시점에 전송하는 MAC 주소를 기반으로 인증
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            String MAC = Objects.requireNonNull(request.getHeaders().get("mac")).toString();
            MAC = MAC.replace("[", "");
            MAC = MAC.replace("]", "");
            if(userDeviceService.authenticateAndRegisterDevice(MAC)) {
                log.info("device connection established from " + MAC);
                return true;
            }
            log.info("device connection attempt from disallowed address " + MAC);
            return false;
        } catch (NullPointerException e) {
            log.info("required header missing");
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    log.info("핸드셰이크 끝나고" + request.getHeaders().toString());
    log.info("경로" + request.getURI());

    String sessionId = ((ServletServerHttpRequest) request).getServletRequest().getSession().getId();
    log.info("세션아이디 : " + sessionId);

    }
}
