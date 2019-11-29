package com.rasp.server.configuration;

import com.rasp.server.repo.CommandLogRepository;
import com.rasp.server.repo.ConditionSettingsRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.rasp.server.repo.EventLogRepository;
import com.rasp.server.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Context {

    @Bean
    EventLogService groundDataService(
            EventLogRepository eventLogRepository,
            @Value("${service.windowTimeout.seconds}") long windowTimeout) {
        return new EventLogService(eventLogRepository, windowTimeout);
    }

    @Bean
    OptimalConditionService optimalConditionService(
            EventLogService eventLogService,
            ConditionSettingsService settingsService
    ) {
        return new OptimalConditionService(eventLogService, settingsService);
    }

    @Bean
    SchedulerCommandService schedulerCommandService(
            OptimalConditionService optimalConditionService,
            ConditionSettingsService settingsService,
            ServoCommandExecutor servoCommandExecutor,
            @Value("${service.commands.cron}") String cron) {
        return new SchedulerCommandService(optimalConditionService, settingsService, servoCommandExecutor, cron);
    }

    @Bean
    ServoCommandExecutor servoCommandExecutor(CommandLogRepository commandLogRepository, SimpMessagingTemplate template) {
        return new ServoCommandExecutor(commandLogRepository, template);
    }

    @Bean
    ConditionSettingsService conditionSettingsService(ConditionSettingsRepository conditionSettingsRepository, @Value("${service.settings.profile}") String settingName) {
        return new ConditionSettingsService(conditionSettingsRepository, settingName);
    }

    @Bean
    TestUtilService testUtilService(
            EventLogRepository eventLogRepository,
            ConditionSettingsRepository conditionSettingsRepository) {
        return new TestUtilService(eventLogRepository, conditionSettingsRepository);
    }
}
