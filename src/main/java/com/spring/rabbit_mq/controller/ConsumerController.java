package com.spring.rabbit_mq.controller;

import com.spring.rabbit_mq.model.Message;
import com.spring.rabbit_mq.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/messages/{queueName}")
    public List<Message> getMessage(@PathVariable String queueName){
        return consumerService.receiveMessages(queueName);

    }
}
