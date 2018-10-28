package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.message.Message;
import datalayer.data.message.TypeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class SendMessage implements Command {
    private static final String KEY_MESSAGE = "text";
    private static final String KEY_USERNAME = "username";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        String textMessage = (String) req.getAttribute(KEY_MESSAGE);
        String username = (String) req.getSession().getAttribute(KEY_USERNAME);

        Message message = new Message(textMessage,
                TypeMessage.MASSAGE, username, new Date());
        messageDAO.addMessage(message);

        Message[] messages = messageDAO.getAllMessages();

        String messagesJson = mapper.writeValueAsString(messages);
        resp.getWriter().write(messagesJson);
    }
}
