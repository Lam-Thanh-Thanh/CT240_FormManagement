package com.example.demo.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) //Bật CORS
            .csrf(csrf -> csrf.disable())  // Vẫn giữ tắt CSRF
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()  // Cho phép tất cả yêu cầu đến API auth
                .requestMatchers("/api/responses/**").permitAll() //  Mở API khảo sát
                .requestMatchers("/api/reports/**").permitAll() //  Mở API thống kê
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()// Mở Swagger
                .anyRequest().authenticated())  // Các request khác cần authentication
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Không lưu session
            .httpBasic(httpBasic -> httpBasic.disable())  // Tắt xác thực HTTP Basic
            .formLogin(form -> form.disable()); // Tắt form login mặc định của Spring Security

        return http.build();
    }

    // Cấu hình CORS để cho phép frontend truy cập API
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500")); // 🚀 Thêm domain frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // Hỗ trợ gửi cookie/token nếu cần

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
