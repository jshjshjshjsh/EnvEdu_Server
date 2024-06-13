package com.example.demo.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/dynamodb/**")
//                        .filters(f -> f.filter(new AuthenticationFilter())) // 인증 필터 적용
                        .uri("ec2-43-202-179-34.ap-northeast-2.compute.amazonaws.com")) // 해당 서비스의 실제 주소
                .build();
    }
}
