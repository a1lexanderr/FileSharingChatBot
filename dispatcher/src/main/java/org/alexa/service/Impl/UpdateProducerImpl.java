package org.alexa.service.Impl;


import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.alexa.config.RabbitMQConfig;
import org.alexa.service.UpdateProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class UpdateProducerImpl implements UpdateProducer {
    private final AmqpTemplate amqpTemplate;
    public UpdateProducerImpl(final AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }



    @Override
    public void send(String rabbitQueue, Update update) {
        amqpTemplate.convertAndSend(rabbitQueue, update);
    }
}

