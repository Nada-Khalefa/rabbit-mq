package com.spring.rabbit_mq.controller;

import com.spring.rabbit_mq.model.MessageDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/fanout")
public class FanoutExchangeController {


    @Autowired
    private AmqpTemplate fanoutQueue;


    @GetMapping("/message")
    public String sendMessage() {

        MessageDto messageDto = new MessageDto("fanout", LocalDateTime.now());
        fanoutQueue.convertAndSend(messageDto);

        return "Success Fanout Exchange";

    }



}
