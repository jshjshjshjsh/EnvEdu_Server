package com.example.demo.seed.socket.controller;

import com.example.demo.jwt.util.JwtUtil;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.repository.SeedRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        log.info("device 전달 완료");
//        log.info("seed.getMac = " + seed.getMac() + ", seed.getCo2 = " + seed.getCo2() + ", seed.getHum = " + seed.getHum() + ", seed.getDust = " + seed.getDust());
//        String username = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap().get(JwtUtil.claimUsername).toString();
//        seed.updateUsername(username);

        log.info("device 현재 시간 : " +LocalDateTime.now(ZoneId.of("Asia/Seoul")) );
        log.info("seed.getMac : " +seed.getMac() );
        log.info("seed : " +seed );


        template.convertAndSend("/topic/user/" + seed.getMac(), seed);
    }

    @MessageMapping("/switch")
    private void fromEClassClient(@Payload String switchMessage, SimpMessagingTemplate template) {
        log.info("device 전달 완료 : " + switchMessage);

        // 여기에서 "newPage" 대신 실제 전송하고자 하는 메시지 내용을 명시적으로 지정해야 합니다.
        template.convertAndSend("/topic/switchPage", switchMessage);
    }


}