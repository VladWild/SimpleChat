package datalayer.type.jvm;

import datalayer.MessageDAO;
import datalayer.data.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class JvmMessageDAO implements MessageDAO {
    private static JvmMessageDAO jvmMessageDAO;

    private List<Message> messages = new ArrayList<>();

    public static synchronized JvmMessageDAO getInstance(){
        if (jvmMessageDAO == null){
            jvmMessageDAO = new JvmMessageDAO();
        }

        return jvmMessageDAO;
    }

    private JvmMessageDAO(){

    }

    @Override
    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public Message[] getAllMessages() {
        return messages.toArray(new Message[0]);
    }
}
