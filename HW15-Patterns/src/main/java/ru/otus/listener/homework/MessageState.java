package ru.otus.listener.homework;

import ru.otus.model.Message;

public class MessageState {

    private final Message message;

    public MessageState(Message message) {
        this.message = message;
    }

    public long getId() {
        return message.getId();
    }
}
