package com.rasp.server.dto;

import com.rasp.server.constant.EventType;
import lombok.Data;

@Data
public class ParamRequest {

    private EventType name;

}