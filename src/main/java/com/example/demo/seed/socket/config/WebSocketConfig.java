package com.example.demo.seed.socket.config;

import com.example.demo.device.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Slf4j
    public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final UserDeviceRepository userDeviceRepository;

    /**
     * tcp handshake, websocket handshake가 끝나고 STOMP를 이용해 전송한 메세지가 도착할 때 실행됨
     * 이전에는 이 부분에서 기기의 MAC을 이용해 접근 허용 제어
     * 지금은 해당 기능을 각 endpoint의 interceptor에서 수행
     */
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                return message;
//            }
//        });
//    }

    // Stomp 사용해서 아두이노 기기와 연결해주는 메서드
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //아두이노 기기와 연결되는 endpoint
        registry.addEndpoint("/device").setAllowedOriginPatterns("*").addInterceptors(new DeviceSocketInterceptor(userDeviceRepository)).withSockJS();
//
//        //프론트와 연결되는 endpoint
//        registry.addEndpoint("/client/socket").setAllowedOriginPatterns("*").addInterceptors(new SocketConnectionInterceptor()).withSockJS();
        registry.addEndpoint("/client/socket")
                .setAllowedOrigins("http://localhost:3000") // 여기에 필요한 출처를 명시적으로 추가
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}