package chat.commands;

import chat.Command;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.massage.Message;
import datalayer.data.massage.TypeMessage;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class Logout implements Command {
    private static final String LOGOUT_MESSAGE = "User \"%s\" is logout from chat";

    private static final Logger logger = Logger.getLogger(Logout.class);

    private void executeLogout(HttpServletRequest req, User user, UserDAO userDAO, MessageDAO messageDAO){
        userDAO.removeUserByName(user.getName());
        logger.debug("Remove user from DAO: " + user.toString());

        req.getSession().invalidate();
        logger.debug("Remove session");

        Message message = new Message(String.format(LOGOUT_MESSAGE, user.getName()),
                TypeMessage.LOGOUT, user.getName(), new Date());
        messageDAO.addMessage(message);
        logger.debug("Add message in DAO: " + message.toString());
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO) {
        User user = getUserFromSession(req);

        executeLogout(req, user, userDAO, messageDAO);
    }
}

