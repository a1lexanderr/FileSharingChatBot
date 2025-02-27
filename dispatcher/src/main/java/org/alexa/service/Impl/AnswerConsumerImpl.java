package org.alexa.service.Impl;

import org.alexa.controller.UpdateController;
import org.alexa.service.AnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateController updateController;
    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }
    @Override
    @RabbitListener(queues = "answer-queue")
    public void consume(SendMessage sendMessage) {
        updateController.setAnswer(sendMessage);
    }
}
