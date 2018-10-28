package datalayer;

import datalayer.data.message.Message;

import java.util.List;
import java.util.Set;

public interface MessageDAO {
    void addMessage(Message message);
    Message[] getAllMessages();
}
