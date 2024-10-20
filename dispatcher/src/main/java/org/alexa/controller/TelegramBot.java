package org.alexa.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot{
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    private UpdateController updateController;
    public TelegramBot(UpdateController updateController) {
        this.updateController = updateController;
    }
    @PostConstruct
    public void init(){
        updateController.setTelegramBot(this);
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateController.processUpdate(update);
//        var response = new SendMessage();
//        response.setChatId(originalMessage.getChatId());
//        response.setText(originalMessage.getText());
//        sendAnswer(response);
    }

    public void sendAnswer(SendMessage message){
        if(message != null){
            try{
                execute(message);
            }
            catch(Exception e){
                log.error(e.getMessage());
            }
        }
    }




    @Override
    public String getBotToken(){
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
