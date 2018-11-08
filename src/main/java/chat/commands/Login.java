package chat.commands;

import chat.Command;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.massage.Message;
import dto.chat.InitializationData;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class Login implements Command {
    private static final Logger logger = Logger.getLogger(Login.class);

    private void setResponseLogin(HttpServletResponse resp, User user, UserDAO userDAO, MessageDAO messageDAO) throws IOException {
        Message[] messages = messageDAO.getAllMessages();
        logger.debug("Get messages from DAO: " + Arrays.stream(messages).map(Message::toString).reduce("", (log, message) -> log + message));
        String[] users = userDAO.getAllUserNamesExceptIn(user.getName());
        logger.debug("Get users from DAO: " + Arrays.stream(users).reduce("", (log, currentUser) -> log + currentUser));

        InitializationData initializationData = new InitializationData(user.getName(), messages, users);
        logger.debug("Initialization data DTO: " + initializationData.toString());

        sendData(resp, initializationData);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO) throws IOException {
        User user = getUserFromSession(req);

        setResponseLogin(resp, user, userDAO, messageDAO);
    }
}

