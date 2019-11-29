package com.rasp.server.service;

import com.rasp.server.constant.CommandType;
import com.rasp.server.dto.Greeting;
import com.rasp.server.repo.CommandLogRepository;
import com.rasp.server.repo.tables.Commands;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
public class ServoCommandExecutor {
    private final CommandLogRepository commandLogRepository;
    private final SimpMessagingTemplate template;

    public void executeCommand(CommandType commandType) {
        //TODO API ON HARDWARE TBD, DO ASYNC
        System.out.println("GOING EXEC COMMAND " + commandType);
        Commands entity = new Commands(UUID.randomUUID(), Date.from(Instant.now()).getTime(), commandType.name());
        commandLogRepository.save(entity);
        template.convertAndSend("/topic/signals", entity);
    }
}
