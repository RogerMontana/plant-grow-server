package com.rasp.server.api;

import com.rasp.server.dto.Greeting;
import com.rasp.server.dto.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class GreetingController {

    private AtomicInteger atomicInt = new AtomicInteger(0);

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        int counter = atomicInt.incrementAndGet();
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "! message num" + counter);
    }
}