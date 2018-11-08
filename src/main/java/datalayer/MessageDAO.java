package datalayer;

import datalayer.data.massage.Message;

public interface MessageDAO {
    void addMessage(Message message);
    Message[] getAllMessages();
}
