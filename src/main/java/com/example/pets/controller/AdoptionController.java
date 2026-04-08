package com.example.pets.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdoptionController {
    
    private final ChatClient ai;

    AdoptionController (MessageChatMemoryAdvisor promptChatMemoryAdvisor,
                         ChatClient.Builder ai  ) {
        this.ai = ai
                .defaultAdvisors(promptChatMemoryAdvisor)
                .build();
    }
    

    @GetMapping("/{user}/assistant")
    String inquire(@PathVariable String user, @RequestParam String question) {
        return ai
                .prompt()
                .user(question)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user)) // new
                .call()
                .content();

    }

    
}
