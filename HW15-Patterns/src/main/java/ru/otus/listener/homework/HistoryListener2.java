package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.*;

public class HistoryListener2 implements Listener, HistoryReader {

    private final Map<Long, Message> cash = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        cash.put(msg.getId(), copyMessage(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(cash.get(id));
    }


    private static Message copyMessage(Message message) {
        return message.toBuilder().build();
    }

}
