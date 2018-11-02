package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.message.Message;
import datalayer.data.message.TypeMessage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class Logout implements Command {
    private static final String LOGOUT_MESSAGE = "User \"%s\" is logout from chat";

    private static final Logger logger = Logger.getLogger(Logout.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        User user = (User) req.getSession().getAttribute(USERNAME);
        logger.debug("Get user from current session: " + user.toString());

        userDAO.removeUserByName(user.getName());
        logger.debug("Remove user from DAO: " + user.toString());
        req.getSession().invalidate();
        logger.debug("Remove session");

        Message message = new Message(String.format(LOGOUT_MESSAGE, user.getName()),
                TypeMessage.LOGOUT, user.getName(), new Date());
        messageDAO.addMessage(message);
        logger.debug("Add message in DAO: " + message.toString());
    }
}
