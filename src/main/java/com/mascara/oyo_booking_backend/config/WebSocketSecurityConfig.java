//package com.mascara.oyo_booking_backend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.Message;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
//
///**
// * Created by: IntelliJ IDEA
// * User      : boyng
// * Date      : 13/06/2024
// * Time      : 10:08 CH
// * Filename  : WebSocketSecurityConfig
// */
//@Configuration
//@EnableWebSocketSecurity
//public class WebSocketSecurityConfig {
//    @Bean
//    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        messages
//                .simpDestMatchers("/user/**").hasRole("USER")
//
//        return messages.build();
//    }
//}
