package org.alexa.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void consumeTextMessageUpdate(Update update);
    void consumeAudioMessageUpdate(Update update);
    void consumePhotoMessageUpdate(Update update);
    void consumeDocMessageUpdate(Update update);
    void consumeVideoMessageUpdate(Update update);

}
