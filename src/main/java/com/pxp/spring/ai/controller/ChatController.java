package com.pxp.spring.ai.controller;

import com.pxp.spring.ai.model.UserMessageModel;
import com.pxp.spring.ai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Bean
    public RouterFunction<ServerResponse> endpoints() {
        return RouterFunctions.route()
                .POST("chat", request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(chatService.chat(request.body(UserMessageModel.class).message()))
                )
                .build();
    }
}
