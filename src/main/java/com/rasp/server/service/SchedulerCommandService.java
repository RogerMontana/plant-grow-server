package com.rasp.server.service;

import com.rasp.server.constant.EventType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@AllArgsConstructor
@Slf4j
public class SchedulerCommandService {
    private final OptimalConditionService conditionService;
    private final ConditionSettingsService settingsService;
    private final ServoCommandExecutor commandExecutor;
    private final String cron;

    @Scheduled(cron = "${service.commands.cron}")
    public void scheduleTaskWithCronExpression() {
        Stream.of(
                settingsService.getActiveParams().stream()
                        .map(params ->
                                conditionService.evaluateCommandOnEvent(EventType.valueOf(params.getParam()))
                        )
        ).flatMap(Function.identity())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(commandExecutor::executeCommand);
    }

    public String getCronConfig() {
        return cron;
    }

}
