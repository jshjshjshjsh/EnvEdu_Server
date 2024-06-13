package com.example.demo.jpa.seed.socket.interceptor;

import com.example.demo.jpa.device.model.UserDevice;
import com.example.demo.jpa.device.repository.UserDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;



// 클라이언트에서 만드는 소켓 커넥션에서 디바이스 커넥션이 생성 되었는지를 확인해서 디바이스 커넥션이 생성 되었으면 커넥션 생성 허용
@Slf4j
@RequiredArgsConstructor
public class SocketConnectionInterceptor implements HandshakeInterceptor {

    private final UserDeviceRepository userDeviceRepository;

    public static String getDeviceName(String query) {
        String[] params = query.split("&");
        for (String param : params) {
            if (param.startsWith("deviceName=")) {
                String value = param.split("=")[1];
                return URLDecoder.decode(value, StandardCharsets.UTF_8); // URL 디코딩을 통해 공백 등을 정확히 처리
            }
        }
        return null; // deviceName 파라미터가 없을 경우
    }


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        try {
            String deviceName = request.getURI().getQuery();

            String deviceNameParsed = getDeviceName(deviceName);
            log.info("deviceName? : " + deviceNameParsed);

            UserDevice userDevice = userDeviceRepository.findByName(deviceNameParsed);

            if(userDevice.isDeviceOn()){
                log.info("디바이스 연결 있음");
                return true;
            }else{
                log.info("디바이스 연결 없음");
                return false;
            }

        } catch (NullPointerException e) {
            log.info("required header missing");
            return false;
        }


    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
}
