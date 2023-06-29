package com.example.demo.seed.socket.controller;

import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.seed.model.Seed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessageController {
    private final SimpMessagingTemplate template;

    /**
     * 기기에서 전송하는 메세지를 받는 controller
     * 기기에서 전송하는 데이터에는 날짜 정보가 없음 -> 여기서 날짜 정보를 추가해 프론트로 전송
     */
    @MessageMapping("/device")
    private void fromESP2Client(@Payload Seed seed) {
        log.info("seed.getMac = " + seed.getMac() + ", seed.getCo2 = " + seed.getCo2() + ", seed.getHum = " + seed.getHum() + ", seed.getDust = " + seed.getDust());
        //String username = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap().get(JwtUtil.claimUsername).toString();
        //seed.updateUsername(username);

        //seed.setDateString(LocalDateTime.now(ZoneId.of("Asia/Seoul")).toString());
        template.convertAndSend("/topic/user/" + seed.getMac(),seed);
    }
}
