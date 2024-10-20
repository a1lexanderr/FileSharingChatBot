package org.alexa.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.alexa.dao.RawDataDAO;
import org.alexa.entity.RawData;
import org.alexa.service.MainService;
import org.alexa.service.ProducerService;
import org.alexa.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final MessageUtils messageUtils;
    public MainServiceImpl(final RawDataDAO rawDataDAO, ProducerService producerService, MessageUtils messageUtils) {
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
        this.messageUtils = messageUtils;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
    }

    private void saveRawData(Update update) {
        RawData rawData = RawData.builder().update(update).build();
        rawDataDAO.save(rawData);
        log.info("Producing answer for update id {} to the broker", update.getUpdateId());
        var message = messageUtils.getSendMessage(update, "Saving your data to the database");
        producerService.produceAnswer(message);
    }
}
