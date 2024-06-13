package com.mascara.oyo_booking_backend.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/06/2024
 * Time      : 11:25 CH
 * Filename  : CustomInterceptor
 */
@Component
public class CustomInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        if (request.getHeaders() == null)
            return;
        if (request.getHeaders().get("Sec-WebSocket-Protocol") == null)
            return;
        String protocol = (String) request.getHeaders().get("Sec-WebSocket-Protocol").get(0);
        if (protocol == null)
            return;
        response.getHeaders().add("Sec-WebSocket-Protocol", protocol);
    }
}
