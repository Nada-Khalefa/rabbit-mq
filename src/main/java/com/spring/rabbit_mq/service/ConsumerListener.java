package com.spring.rabbit_mq.service;

import com.spring.rabbit_mq.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerListener {


    @RabbitListener(queues = {"${rabbit.direct1.queue}", "${rabbit.direct3.queue}"},
    containerFactory = "simpleRabbitListenerContainerFactory")
    public void receiveMessages(Message message){
        System.out.println(message.getStatus()+" "+ message.getLocalDateTime());
        throw new RuntimeException();

    }
}
