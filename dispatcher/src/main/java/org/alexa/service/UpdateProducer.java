package org.alexa.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducer {
    void send(String rabbitQueue, Update update);
}
