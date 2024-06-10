package com.example.demo.security.config;

import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static com.example.demo.security.config.AuthenticationFilterApply.authenticationFilterApply;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }
//    private final CorsConfig corsConfig;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors().configurationSource(corsConfigurationSource());

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(authenticationFilterApply())
                .and()
                .apply(AuthorizationFilterApply.getInstance())
                .and()
                .logout().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers("/login/**","/user","/auth","/register/**","/logout","/device/**","/ws/**","/client/socket/**","/test/**",
                                "/air-quality/**","/ocean-quality/**","/survey/**" ).permitAll()
                        .mvcMatchers("/educating/**","/dataLiteracy/**","/classroom/**","/dataset/list").permitAll()
                        .mvcMatchers("/seed/**","/air-quality/mine", "/ocean-quality/mine","/user/**",
                                "/datafolder/**","/mydata/**","/dataupload/**", "/student/join/**").hasAnyRole("STUDENT","EDUCATOR","MANAGER","ADMIN")
                        .mvcMatchers("/educator/**").hasAnyRole("EDUCATOR","MANAGER","ADMIN")
                        //.mvcMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
                        .mvcMatchers("/manager/**","/dataset/manage/**").hasAnyRole("MANAGER","ADMIN")
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().denyAll()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowedOrigins(Arrays.asList("https://new.greenseed.or.kr"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // 허용할 HTTP 메소드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격증명 허용
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type",JwtUtil.headerString)); // 클라이언트가 접근 가능하도록 노출할 헤더

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 설정 적용
        return source;
    }
}
