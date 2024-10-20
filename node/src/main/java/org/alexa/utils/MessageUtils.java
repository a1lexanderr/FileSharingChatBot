package org.alexa.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageUtils {
    public SendMessage getSendMessage(Update update, String text) {
        var message = update.getMessage();
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(text);
        return sendMessage;

    }
}