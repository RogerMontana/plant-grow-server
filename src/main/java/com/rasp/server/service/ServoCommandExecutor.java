package com.rasp.server.service;

import com.rasp.server.constant.CommandType;
import com.rasp.server.repo.CommandLogRepository;
import com.rasp.server.repo.tables.Commands;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;


@AllArgsConstructor
@Slf4j
public class ServoCommandExecutor {
    private final CommandLogRepository commandLogRepository;
    private final SimpMessagingTemplate template;

    public void executeCommand(CommandType commandType) {
        //TODO API ON HARDWARE TBD, DO ASYNC
        Commands entity = new Commands(UUID.randomUUID(), Date.from(Instant.now()).getTime(), commandType.name());
        commandLogRepository.save(entity);
        log.info("Exec Command {}", entity);
        template.convertAndSend("/topic/signals", entity);
    }
}
