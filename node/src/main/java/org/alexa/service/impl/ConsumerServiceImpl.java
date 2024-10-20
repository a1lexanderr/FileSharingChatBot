package org.alexa.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.alexa.service.ConsumerService;
import org.alexa.service.MainService;
import org.alexa.service.ProducerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
    private final ProducerService producerService;
    private final MainService mainService;
    public ConsumerServiceImpl(ProducerService producerService, MainService mainService) {
        this.producerService = producerService;
        this.mainService = mainService;
    }

    @Override
    @RabbitListener(queues = "text-queue")
    public void consumeTextMessageUpdate(Update update) {
        log.info("Consuming text message update");
        mainService.processTextMessage(update);

    }

    @Override
    @RabbitListener(queues = "audio-queue")
    public void consumeAudioMessageUpdate(Update update) {
        log.info("Consuming audio message update");
        mainService.processTextMessage(update);
    }


    @Override
    @RabbitListener(queues = "photo-queue")
    public void consumePhotoMessageUpdate(Update update) {
        log.info("Consuming photo message update");
        mainService.processTextMessage(update);
    }

    @Override
    @RabbitListener(queues = "document-queue")
    public void consumeDocMessageUpdate(Update update) {
        log.info("Consuming doc message update");
        mainService.processTextMessage(update);
    }

    @Override
    @RabbitListener(queues = "video-queue")
    public void consumeVideoMessageUpdate(Update update) {
        log.info("Consuming video message update");
        mainService.processTextMessage(update);
    }
}
