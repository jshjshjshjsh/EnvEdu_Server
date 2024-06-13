package com.example.demo.jpa.seed.socket.controller;

import com.example.demo.jpa.seed.model.Seed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private void fromEClassClient(@Payload String switchMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        String pageValue = null;
        try {
            JsonNode rootNode = objectMapper.readTree(switchMessage);
            pageValue = rootNode.path("page").asText(); // "newPage" 값을 가져옵니다.
            log.info("device 전달 완료 : " + pageValue);
        } catch (JsonProcessingException e) {
            log.error("JSON 파싱 오류", e);
        }


        assert pageValue != null;
        template.convertAndSend("/topic/switchPage", pageValue);

    }


}