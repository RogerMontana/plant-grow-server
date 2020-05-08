package com.rasp.server.service;

import com.rasp.server.constant.EventType;
import com.rasp.server.repo.EventLogRepository;
import com.rasp.server.repo.tables.Records;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class EventLogService {
    private final EventLogRepository eventLogRepository;
    private long timeWindow;

    public List<Records> getAllParams() {
        return eventLogRepository.findAll().collectList().block();
    }

    //LIKE SLIDING WINDOW BY TIME ALG
    public Records getCurrentRecords(EventType eventType) {
        return eventLogRepository.findAll()
                .filter(r -> r.getParam().contains(eventType.toString()) && r.getTimestamp() > (Date.from(Instant.now().minusSeconds(timeWindow)).getTime()))
                .toStream().max((Comparator.comparing(Records::getTimestamp)))
                .orElseThrow(() -> new NoSuchElementException(String.format("No data for %s in %d sec window", eventType, timeWindow)));
    }

}
