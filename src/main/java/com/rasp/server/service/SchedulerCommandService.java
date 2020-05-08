package com.rasp.server.service;

import com.rasp.server.constant.EventType;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@AllArgsConstructor
public class SchedulerCommandService {
    private OptimalConditionService conditionService;
    private ConditionSettingsService settingsService;
    private ServoCommandExecutor commandExecutor;
    private String cron;

    @Scheduled(cron = "${service.commands.cron}")
    public void scheduleTaskWithCronExpression() {
        System.out.println("EXEC COMMANDS");
        Stream.of(
                settingsService.getActiveParams().stream()
                        .peek(System.out::println)
                        .map(params ->
                                conditionService.evaluateCommandOnEvent(EventType.valueOf(params.getParam()))
                        )
        ).flatMap(Function.identity())
//                .peek(System.out::println)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(commandExecutor::executeCommand);
    }

    public String getCronConfig() {
        return cron;
    }

}
