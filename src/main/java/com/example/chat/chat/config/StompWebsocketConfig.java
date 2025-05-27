package com.example.chat.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;


@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompWebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private static final String CLIENT_URI="http://localhost:3000";
    private final StompHandler stompHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/connect")
                .setAllowedOrigins(CLIENT_URI)
                // ws:// 가 아닌 http:// 엔드포인트를 사용할 수 있게 해주는 sockJS 라이브러리를 통한 요청을 허용하는 설정
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // '/publish/1' 형태로 메세지 발행해야 함을 설정
        // '/publish'로 시작하는 url 패턴으로 메세지가 발행되면 @Controller 객체의 @MessageMapping 메서드로 라우팅
        registry.setApplicationDestinationPrefixes("/publish");
        // '/topic/1' 형태로 메세지를 수신(subscribe)해야 함을 설정
        registry.enableSimpleBroker("/topic");
    }

    // 웹소캣 요청(connect, subscribe, disconnect 등)의 요청 시에는 http header 등 http 메세지를 넣어올 수 있고, 이를 interceptor를 통해 가로채 토큰 등으로 인증 처리 할 수 있다.
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }

}
