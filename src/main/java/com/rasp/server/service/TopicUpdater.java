package com.rasp.server.service;

import com.rasp.server.dto.Greeting;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@AllArgsConstructor
public class TopicUpdater {

    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay = 3000)
    public void publishUpdates() {
        template.convertAndSend("/topic/greetings", new Greeting(String.valueOf(LocalTime.now())));
    }

}
