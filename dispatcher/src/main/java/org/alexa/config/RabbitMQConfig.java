package org.alexa.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class RabbitMQConfig {
    private final RabbitMQPropConfig rabbitMQPropConfig;

    public RabbitMQConfig(final RabbitMQPropConfig rabbitMQPropConfig) {
        this.rabbitMQPropConfig = rabbitMQPropConfig;
    }

    @Bean
    public Map<String, Queue> queues(){
        return rabbitMQPropConfig.getQueue().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new Queue(entry.getValue())));
    }

    @Bean
    public Queue textQueue() {
        return new Queue(rabbitMQPropConfig.getQueue().get("text"));
    }

    @Bean
    public Queue photoQueue() {
        return new Queue(rabbitMQPropConfig.getQueue().get("photo"));
    }

    @Bean
    public Queue audioQueue() {
        return new Queue(rabbitMQPropConfig.getQueue().get("audio"));
    }

    @Bean
    public Queue videoQueue() {
        return new Queue(rabbitMQPropConfig.getQueue().get("video"));
    }

    @Bean
    public Queue documentQueue() {
        return new Queue(rabbitMQPropConfig.getQueue().get("document"));
    }

    @Bean
    public Queue answerQueue() {
        return new Queue(rabbitMQPropConfig.getQueue().get("answer"));
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


}
