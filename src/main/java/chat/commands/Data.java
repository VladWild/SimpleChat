package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import dto.output.DynamicData;
import datalayer.data.message.Message;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class Data implements Command {
    private static final Logger logger = Logger.getLogger(Data.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        User user = (User) req.getSession().getAttribute(USERNAME);
        logger.debug("Get user from current session: " + user.toString());

        Message[] messages = messageDAO.getAllMessages();
        logger.debug("Get messages from DAO: " + Arrays.stream(messages).map(Message::toString).reduce("", (log, currentMessage) -> log + currentMessage));
        String[] users = userDAO.getAllUserNamesExceptIn(user.getName());
        logger.debug("Get all users for \"" + user.getName() + "\": " + Arrays.stream(users).reduce("", (log, currentUser) -> log + currentUser));

        DynamicData dynamicData = new DynamicData(messages, users);
        logger.debug("Create \"DynamicData\" DTO: " + dynamicData.toString());

        String dynamicDataJson = mapper.writeValueAsString(dynamicData);
        logger.debug("Messages data in JSON format for output: " + dynamicDataJson);
        resp.getWriter().write(dynamicDataJson);
    }
}
