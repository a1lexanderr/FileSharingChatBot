package org.alexa.service.impl;

import org.alexa.service.ProducerService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class ProducerServiceImpl implements ProducerService {
    private final AmqpTemplate amqpTemplate;
    @Value("${rabbitmq.queue.answer}")
    private String queueName;

    public ProducerServiceImpl(final AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        amqpTemplate.convertAndSend(queueName, sendMessage);
    }
}
