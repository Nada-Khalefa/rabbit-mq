package com.spring.rabbit_mq.controller;

import com.spring.rabbit_mq.model.MessageDto;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/default-exchange")
public class DefaultExchangeController {

    /*
    * AmqpTemplate -> to send message inside queue
    * with the same name of Bean {defaultQueue}
     @Bean
     public AmqpTemplate defaultQueue

     */
    @Autowired
    private AmqpTemplate defaultQueue;

    // function send message in queue
    @GetMapping("/message")
    public void sendMessage(){
        MessageDto messageDto = new MessageDto("default", LocalDateTime.now());
        defaultQueue.convertAndSend(messageDto);
    }
}
