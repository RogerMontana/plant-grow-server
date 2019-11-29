package com.rasp.server.api;

import com.rasp.server.constant.EventType;
import com.rasp.server.constant.UnitType;
import com.rasp.server.dto.Greeting;
import com.rasp.server.dto.HelloMessage;
import com.rasp.server.dto.ParamInfoDto;
import com.rasp.server.dto.ParamRequest;
import com.rasp.server.repo.tables.Records;
import com.rasp.server.service.EventLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequiredArgsConstructor
public class SignalsSoketController {

    private EventLogService eventLogService;

    @MessageMapping("/param")
    @SendTo("/topic/signals")
    public ParamInfoDto greeting(ParamRequest message) throws Exception {
        Thread.sleep(1000); // simulated delay
        Records currentRecords = eventLogService.getCurrentRecords(message.getName());
        return new ParamInfoDto(EventType.valueOf(currentRecords.getParam()),
                currentRecords.getValue(),
                UnitType.valueOf(currentRecords.getUnit()));
    }
}