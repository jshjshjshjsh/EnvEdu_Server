package com.example.demo.jpa.seed.socket.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
@Slf4j
public class StompChannelInterceptor implements ChannelInterceptor {

    private Map<String, String> sessionUsers = new ConcurrentHashMap<>();  // 세션 ID와 연결된 사용자 정보를 저장

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

            // 디바이스의 mac 주소를 식별자로 사용함
            String mac = String.valueOf(message.getHeaders());
            String macAddress = extractMacAddress(mac);
            log.info("헤더의 mac : " + macAddress);

            sessionUsers.put(mac, sessionId);

            String checkMac = sessionUsers.get(mac);
            log.info("Connected session ID: " + sessionId + " mac: " + macAddress);
            log.info("checkMac : " + checkMac);
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            // 연결 종료 시 세션 제거
            sessionUsers.remove(sessionId);
            log.info("Disconnected session ID: " + sessionId);
        }
        return message;
    }
    private String extractMacAddress(String data) {
        String key = "MAC=[";  // 검색할 키워드
        int start = data.indexOf(key) + key.length();  // 'MAC=[' 다음 시작 인덱스
        int end = data.indexOf("]", start);  // 시작 인덱스 이후 첫 번째 ']' 인덱스
        return data.substring(start, end);  // MAC 주소 추출
    }


    public boolean isUserConnected(String sessionId) {
        return sessionUsers.containsKey(sessionId);
    }

    public String getSessionIdByMac(String mac) {
        return sessionUsers.get(mac);
    }

}

