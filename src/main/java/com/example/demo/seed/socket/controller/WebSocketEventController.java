package com.example.demo.seed.socket.controller;

import com.example.demo.device.service.UserDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventController {

    private final ConcurrentHashMap<String, String> deviceSessions = new ConcurrentHashMap<>();
    private final UserDeviceService userDeviceService;


    /**
     * 해당 컨트롤러는 디바이스의 연걸의 세션을 관리하는 핸들러 컨트롤러입니다.
     * @param event
     * @author 김선규
     */
    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {

        Message<?> message = event.getMessage();
        GenericMessage<byte[]> GenericMessage = (org.springframework.messaging.support.GenericMessage<byte[]>) message.getHeaders().get("simpConnectMessage");
        Map<String, Object> messageData = GenericMessage.getHeaders();
        Map<String, List<String>> nativeHeaders = (Map<String, List<String>>) messageData.get("nativeHeaders");
        List<String> macList = nativeHeaders.get("MAC");
        Optional<String> MAC = macList != null && !macList.isEmpty() ? Optional.of(macList.get(0)) : Optional.empty();

        if (MAC.isEmpty()) {
            log.info("클라이언트에서 들어온거로 추정");

        }else{
            String macAddress = MAC.get().replace("[", "").replace("]", "");
            log.info("MAC : {}", macAddress);

            String sessionId = (String) messageData.get("simpSessionId");
            log.info("sessionId : {}", sessionId);

            Object simpHeartbeat = messageData.get("simpHeartbeat");
            log.info("Heartbeat : {} ",simpHeartbeat);

            assert sessionId != null;
            deviceSessions.put(sessionId, macAddress);
            log.info("Session connected: sessionId={}, MAC={}", sessionId, MAC);
        }

    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        assert sessionId != null;
        String MAC = deviceSessions.remove(sessionId);
        if (MAC != null) {
            boolean deviceStatus = userDeviceService.setDeviceOff(MAC);
            log.info("Session disconnected: sessionId={}, MAC={}, status={} ", sessionId, MAC , deviceStatus);
        } else {
            log.warn("Session data not found: {}", sessionId);
        }
    }
}

