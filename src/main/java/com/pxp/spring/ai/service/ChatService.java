package com.pxp.spring.ai.service;

import com.pxp.spring.ai.config.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatService {

    private ChatClient chatClient;

    public ChatService(ChatClient.Builder builder, @Value("classpath:/prompts/System-Message.st") Resource resource) {
        var systemPromptTemplate = new SystemPromptTemplate(resource);
        this.chatClient = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()), new LoggingAdvisor())
                .defaultSystem(systemPromptTemplate.create().getContents())
                .build();
    }

    public Flux<String> chat(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
