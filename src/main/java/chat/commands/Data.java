package chat.commands;

import chat.Command;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.massage.Message;
import dto.chat.DynamicData;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class Data implements Command {
    private static final Logger logger = Logger.getLogger(Data.class);

    private void setResponseData(HttpServletResponse resp, User user, UserDAO userDAO, MessageDAO messageDAO) throws IOException {
        Message[] messages = messageDAO.getAllMessages();
        logger.debug("Get messages from DAO: " + Arrays.stream(messages).map(Message::toString).reduce("", (log, currentMessage) -> log + currentMessage));
        String[] users = userDAO.getAllUserNamesExceptIn(user.getName());
        logger.debug("Get all users for \"" + user.getName() + "\": " + Arrays.stream(users).reduce("", (log, currentUser) -> log + currentUser));

        DynamicData dynamicData = new DynamicData(messages, users);
        logger.debug("Create \"DynamicData\" DTO: " + dynamicData.toString());

        sendData(resp, dynamicData);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO) throws IOException {
        User user = getUserFromSession(req);

        setResponseData(resp, user, userDAO, messageDAO);
    }
}
