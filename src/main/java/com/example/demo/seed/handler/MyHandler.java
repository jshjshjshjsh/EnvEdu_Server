package com.example.demo.seed.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;

@Component
@Slf4j
public class MyHandler extends TextWebSocketHandler {
    private final SimpMessagingTemplate template;

    public MyHandler(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        log.info("아두이노에서 받은 메시지 : " + payload);

        session.sendMessage(new TextMessage("Message received: " + payload));

        // 메시지를 변환하여 /topic/data 경로로 브로드캐스트
        template.convertAndSend("/topic/data", payload);
    }
}
