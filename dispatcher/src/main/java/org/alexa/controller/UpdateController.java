package org.alexa.controller;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.alexa.config.RabbitMQPropConfig;
import org.alexa.service.UpdateProducer;
import org.alexa.utils.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class UpdateController {
    @Setter
    private TelegramBot telegramBot;
    private final UpdateProducer updateProducer;
    private final RabbitMQPropConfig rabbitMQPropConfig;
    private final MessageUtils messageUtils;

    public UpdateController(UpdateProducer updateProducer, RabbitMQPropConfig rabbitMQPropConfig, MessageUtils messageUtils) {
        this.updateProducer = updateProducer;
        this.rabbitMQPropConfig = rabbitMQPropConfig;
        this.messageUtils = messageUtils;
    }

    public void processUpdate(Update update) {
        if(update == null) {
            log.error("Update is null");
            return;
        }
        if(update.hasMessage()){
            distributeMessagesByType(update);
        }
        else{
            log.error("Received unsupported message " + update);
        }


    }

    private void distributeMessagesByType(Update update){
        if(update.getMessage().hasText()){
            processTextMessage(update);
        }
        else if(update.getMessage().hasPhoto()){
            processPhotoMessage(update);
        }
        else if(update.getMessage().hasAudio()){
            processAudioMessage(update);
        }
        else if(update.getMessage().hasVideo()){
            processVideoMessage(update);
        }
        else if(update.getMessage().hasDocument()){
            processDocumentMessage(update);
        }
        else{
            setUnsupportedMessageType(update);
        }
    }

    private void setUnsupportedMessageType(Update update){
        var message = messageUtils.getSendMessage(update, "К сожалению сообщения такого типа пока не поддерживаются!");
        setAnswer(message);
    }

    public void setAnswer(SendMessage sendMessage){
        telegramBot.sendAnswer(sendMessage);
    }

    private void processTextMessage(Update update){
        log.info("Process text message");
        updateProducer.send(rabbitMQPropConfig.getQueue().get("text"), update);
        setRequestIsReceivedAnswer(update);
    }
    private void processPhotoMessage(Update update){
        updateProducer.send(rabbitMQPropConfig.getQueue().get("photo"), update);
        setRequestIsReceivedAnswer(update);
    }


    private void processAudioMessage(Update update){
        updateProducer.send(rabbitMQPropConfig.getQueue().get("audio"), update);
    }
    private void processVideoMessage(Update update){
        updateProducer.send(rabbitMQPropConfig.getQueue().get("video"), update);
    }
    private void processDocumentMessage(Update update){
        updateProducer.send(rabbitMQPropConfig.getQueue().get("document"), update);
    }

    private void setRequestIsReceivedAnswer(Update update) {
        var message = messageUtils.getSendMessage(update, "Запрос получен! Обрабатываем...");
        setAnswer(message);
    }


}
