package com.example.demo.seed.socket.controller;

import com.example.demo.seed.model.Seed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessageController {
    private final SimpMessagingTemplate template;

    @MessageMapping("/device")
    private void fromESP2Client(@Payload Seed seed) {
        log.info("seed.getMac = " + seed.getMac() + ", seed.getCo2 = " + seed.getCo2());
        //seed.setDateString(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString());
        template.convertAndSend("/topic/user/" + seed.getMac(),seed);
    }
}
