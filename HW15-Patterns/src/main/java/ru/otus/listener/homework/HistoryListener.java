package ru.otus.listener.homework;

import java.util.*;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import javax.swing.text.html.Option;

public class HistoryListener implements Listener, HistoryReader {

    private final Deque<Message> stack = new ArrayDeque<>();

    @Override
    public void onUpdated(Message msg) {
        stack.add(msg.toBuilder().build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(restoreMessage(id));
    }


    private static <T> T copyFieldDelta(T toField, T fromField) {
        if (toField == null) {
            return null;
        } else if (toField.equals(fromField)) {
            return null;
        }
        return toField;
    }

    private static ObjectForMessage copyObjectForMessage(ObjectForMessage fromObjectForMessage) {
        if (fromObjectForMessage == null) {
            return null;
        }
        ObjectForMessage toObjectForMessage = new ObjectForMessage();
        toObjectForMessage.setData(new ArrayList<>(fromObjectForMessage.getData()));
        return toObjectForMessage;
    }

    private static Message copyFields(Message toMessage, Message fromMessage) {
        toMessage.toBuilder()
                .field1(copyFieldDelta(fromMessage.getField1(), toMessage.getField1()))
                .field2(copyFieldDelta(fromMessage.getField2(), toMessage.getField2()))
                .field3(copyFieldDelta(fromMessage.getField3(), toMessage.getField3()))
                .field4(copyFieldDelta(fromMessage.getField4(), toMessage.getField4()))
                .field5(copyFieldDelta(fromMessage.getField5(), toMessage.getField5()))
                .field6(copyFieldDelta(fromMessage.getField6(), toMessage.getField6()))
                .field7(copyFieldDelta(fromMessage.getField7(), toMessage.getField7()))
                .field8(copyFieldDelta(fromMessage.getField8(), toMessage.getField8()))
                .field9(copyFieldDelta(fromMessage.getField9(), toMessage.getField9()))
                .field10(copyFieldDelta(fromMessage.getField10(), toMessage.getField10()))
                .field11(copyFieldDelta(fromMessage.getField11(), toMessage.getField11()))
                .field12(copyFieldDelta(fromMessage.getField12(), toMessage.getField12()))
                .field13(copyFieldDelta(fromMessage.getField13(), copyObjectForMessage(toMessage.getField13())))
                .build();
        return toMessage;
    }

    private Message restoreMessage(long id) {
        return stack.stream().filter(entry -> entry.getId() == id)
                .reduce(new Message.Builder(id).build(), HistoryListener::copyFields);
    }



    private Message.Builder getDeltaMessage(Message message) {

    }

}
