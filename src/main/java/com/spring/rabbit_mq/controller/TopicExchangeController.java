package com.spring.rabbit_mq.controller;

import com.spring.rabbit_mq.model.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topic")
public class TopicExchangeController {

    @Autowired
    private AmqpTemplate topicQueue;

    @GetMapping("/message/{key}")
    public String sendMessage(@PathVariable String key){

        Message message= new Message("topic", LocalDateTime.now());
        topicQueue.convertAndSend(key, message);

        return "Success Topic Exchange";

    }



}
