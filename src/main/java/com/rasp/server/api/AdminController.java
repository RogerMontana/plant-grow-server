package com.rasp.server.api;

import com.rasp.server.repo.CommandLogRepository;
import com.rasp.server.repo.ConditionSettingsRepository;
import com.rasp.server.repo.EventLogRepository;
import com.rasp.server.repo.tables.Settings;
import com.rasp.server.service.SchedulerCommandService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {
    private EventLogRepository eventLogRepository;
    private CommandLogRepository commandLogRepository;
    private ConditionSettingsRepository conditionSettingsRepository;
    private SchedulerCommandService schedulerCommandService;
    
    @DeleteMapping("/admin/events/clean")
    public void cleanEvents() {
        eventLogRepository.deleteAll().block();
    }

    @DeleteMapping("/admin/commands/clean")
    public void cleanCommands() {
        commandLogRepository.deleteAll();
    }

    @DeleteMapping("/admin/settings/{name}")
    public void removeSettingsByName(String name) {
        conditionSettingsRepository.deleteById(name);
    }

    @GetMapping("/admin/settings")
    public List<Settings> getAllSettings() {
        return conditionSettingsRepository.findAll();
    }

    @GetMapping("/admin/settings/cron")
    public String getCurrentCronConf() {
        return schedulerCommandService.getCronConfig();
    }
}
