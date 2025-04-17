package com.spring.rabbit_mq.config;

//import org.springframework.amqp.rabbit.annotation.Queue;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultExchangeConfig {

    @Value("${rabbit.default.queue}")
    private String defaultQueue;

    // declare Queue in server
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Bean
    Queue createQueue (){
    /*
    * durable ->  true  -- (enable) broker cannot remove queue when you restart app
    *             false -- broker remove queue when restart app
    * ----------------------------------------------------------------
    * exclusive -> true  -- (enable)current connection only(app) control and access queue && queue deleted when connection went wrong(restart)
    *              false -- any project on the same server can control and access queue && queue not deleted when connection went wrong(restart)
    * -----------------------------------------------------------------------------
    * autoDelete -> true  -- when consumer take all messages in queue then delete queue
    *               false -- when consumer take all messages in queue then leave queue without deleting it
    *
    * */
        return new Queue(defaultQueue,true, false,false);
    }


    @Bean
    public AmqpTemplate defaultQueue(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.setRoutingKey(defaultQueue); // Default Exchange -> Routing Key= Queue name
        return rabbitTemplate;
    }

    // declare Queue in server
    @PostConstruct
    public void init(){
        amqpAdmin.declareQueue(createQueue());
    }



}
