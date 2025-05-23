package com.spring.rabbit_mq.config;

//import com.rabbitmq.client.ConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Bean
    public ConnectionFactory connectionFactory(){
        AbstractConnectionFactory factory= new CachingConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setVirtualHost(virtualHost);
        factory.setUsername(username);
        factory.setPassword(password);
        return factory;
    }

    // to handle with Json Data between rabbit server and application
    @Bean
    public MessageConverter messageConverter(){
        ObjectMapper objectMapper= new ObjectMapper().findAndRegisterModules();
        return  new Jackson2JsonMessageConverter(objectMapper);
    }

    // Listener
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory containerFactory= new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory());
        containerFactory.setMessageConverter(messageConverter());

        containerFactory.setMaxConcurrentConsumers(20); // max consumer to take messages from queue
        containerFactory.setConcurrentConsumers(10); // initial value of consumers
        containerFactory.setPrefetchCount(5); // how many messages that consumer can fetch from queue
        containerFactory.setAutoStartup(true); // Listener work in initialization App

        //containerFactory.setDefaultRequeueRejected(false); // if error happen donot do reconsume in queue

        // Retry only 3 times and take message to Q4
        containerFactory.setAdviceChain(RetryInterceptorBuilder.stateless()
                .maxAttempts(3).recoverer(new RejectAndDontRequeueRecoverer()).build());

        return containerFactory;
    }

}
