package com.chat.chatapp.DefaultStart;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.chat.chatapp.services.ConversationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StartupRunner implements ApplicationRunner {
    ConversationService conversationService;

    @Override
    public void run(ApplicationArguments args) {
        conversationService.checkGlobalConversation();
    }
}
