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

public class Logout implements Command {
    private static final String LOGOUT_MESSAGE = "User \"%s\" is logout from chat";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        String userName = (String) req.getSession().getAttribute(USERNAME);
        req.getSession().setAttribute(USERNAME, null);

        Message message = new Message(String.format(LOGOUT_MESSAGE, userName),
                TypeMessage.LOGIN, userName, new Date());
        messageDAO.addMessage(message);

        userDAO.removeUserByName(userName);
    }
}
