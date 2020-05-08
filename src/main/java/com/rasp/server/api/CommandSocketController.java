package com.rasp.server.api;

import com.rasp.server.constant.CommandType;
import com.rasp.server.dto.CustomUserCommand;
import com.rasp.server.repo.tables.Commands;
import com.rasp.server.service.ServoCommandExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommandSocketController {

    private final ServoCommandExecutor servoCommandExecutor;

    @MessageMapping("/command")
//    @SendTo("/topic/signals")
    public Commands greeting(CustomUserCommand userCommand) {
        log.info("user command: {}", userCommand);
        Commands cmd = new Commands(UUID.randomUUID(), Date.from(Instant.now()).getTime(), userCommand.getCommand());
        servoCommandExecutor.executeCommand(CommandType.valueOf(userCommand.getCommand()));
        return cmd;
    }
}