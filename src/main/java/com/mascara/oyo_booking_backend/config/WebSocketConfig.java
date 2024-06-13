package com.mascara.oyo_booking_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascara.oyo_booking_backend.securities.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/06/2024
 * Time      : 8:20 CH
 * Filename  : WebSocketConfig
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtils jwtUtils;

    @Qualifier("customUserDetailsService")
    private final UserDetailsService userDetailsService;

    @Qualifier("customInterceptor")
    private final HandshakeInterceptor customInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/user","/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173", "https://oyo-nine.vercel.app/")
                .setHandshakeHandler(new DefaultHandshakeHandler())
//                .addInterceptors(customInterceptor)
                .withSockJS();

        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:5173", "https://oyo-nine.vercel.app/")
                .setHandshakeHandler(new DefaultHandshakeHandler());
        ;
//                .addInterceptors(customInterceptor)
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor =
//                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                log.info("Headers: {}", accessor);
//
//                assert accessor != null;
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//
//                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");
//                    assert authorizationHeader != null;
//                    String token = authorizationHeader.substring(7);
//
//                    String username = jwtUtils.getUsernameFromToken(token);
//                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//
//                    accessor.setUser(usernamePasswordAuthenticationToken);
//                }
//
//                return message;
//            }
//
//        });
//    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }
}
