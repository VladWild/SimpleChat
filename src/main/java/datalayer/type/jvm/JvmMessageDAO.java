package datalayer.type.jvm;

import datalayer.MessageDAO;
import datalayer.data.massage.Message;

import java.util.ArrayList;
import java.util.List;

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
    public synchronized void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public synchronized Message[] getAllMessages() {
        return messages.toArray(new Message[0]);
    }
}
