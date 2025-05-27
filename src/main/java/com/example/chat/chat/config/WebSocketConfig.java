package com.example.chat.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final SimpleWebsocketHandler simpleWebsocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // '/connect' url로 websocket 연결이 들어오면 인자로 넘기는 handler 클래스가 처리한다.
        registry.addHandler(simpleWebsocketHandler, "/connect")
                // securityconfig에서의 cors 예외는 http 요청에 대한 예외이다
                // 따라서 websocket 프로토콜에 대한 요청은 대해서는 별로의 cors 설정이 필요하다.
                .setAllowedOrigins("http://localhost:3000");   // 클라이언트 url cors 허용
    }
}
