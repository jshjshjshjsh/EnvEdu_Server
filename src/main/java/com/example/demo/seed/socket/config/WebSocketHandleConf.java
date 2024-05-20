package com.example.demo.seed.socket.config;
import com.example.demo.seed.handler.MyHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@EnableWebSocket
public class WebSocketHandleConf implements WebSocketConfigurer {

    private final MyHandler myHandler;

    public WebSocketHandleConf(MyHandler myHandler) {
        this.myHandler = myHandler;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler, "/device/websocket").setAllowedOrigins("*");
    }

}
