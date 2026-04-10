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
        var system = """
                You are an AI powered assistant to help people adopt a dog from the adoption agency named Pooch Palace with locations in Rio de Janeiro, Mexico City, Seoul, Tokyo, Singapore, New York City, Amsterdam, Paris, Mumbai, New Delhi, Barcelona, London, and San Francisco. Information about the dogs available will be presented below. If there is no information, then return a polite response suggesting we don't have any dogs available.
                """;
        this.ai = ai
                .defaultSystem(system)
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
