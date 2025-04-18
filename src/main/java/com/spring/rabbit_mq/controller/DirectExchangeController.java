package com.spring.rabbit_mq.controller;

import com.spring.rabbit_mq.model.Message;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/direct")
public class DirectExchangeController {


    @Autowired
    private AmqpTemplate directQueue;

    @Value("${rabbit.direct1.bi}")
    private String binding1;

    @Value("${rabbit.direct2.bi}")
    private String binding2;

    @Value("${rabbit.direct3.bi}")
    private String binding3;

    @GetMapping("/message/{num}")
    public String sendMessage(@PathVariable Integer num) throws Exception {// 1 2 3

        String key;
        if(num ==1){
            key=binding1;
        } else if (num == 2) {
            key= binding2;

        } else if (num==3) {
            key=binding3;
        }else {
            throw new Exception("you must enter 1,2 or 3 only");
        }

        Message message= new Message("direct", LocalDateTime.now());
        directQueue.convertAndSend(key, message);

        return "Success Direct Exchange";

    }



}
