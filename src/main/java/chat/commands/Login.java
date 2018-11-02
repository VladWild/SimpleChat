package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ChatController;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import dto.output.InitializationData;
import datalayer.data.message.Message;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class Login implements Command {
    private static final Logger logger = Logger.getLogger(Login.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        User user = (User) req.getSession().getAttribute(USERNAME);
        logger.debug("Get user from current session: " + user.toString());

        Message[] messages = messageDAO.getAllMessages();
        logger.debug("Get messages from DAO: " + Arrays.stream(messages).map(Message::toString).reduce("", (log, message) -> log + message));
        String[] users = userDAO.getAllUserNamesExceptIn(user.getName());
        logger.debug("Get users from DAO: " + Arrays.stream(users).reduce("", (log, currentUser) -> log + currentUser));

        InitializationData initializationData = new InitializationData(user.getName(), messages, users);
        logger.debug("Initialization data DTO: " + initializationData.toString());

        String initializationDataJson = mapper.writeValueAsString(initializationData);
        logger.debug("Initialization data in JSON format for output: " + initializationDataJson);
        resp.getWriter().write(initializationDataJson);
    }
}

