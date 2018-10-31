package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.dto.DynamicData;
import datalayer.data.message.Message;
import datalayer.data.message.TypeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class Kick implements Command {
    private static final String KICK_MESSAGE = "User \"%s\" is kick user \"%s\"";

    private static final String KICK_USERNAME = "kickUserName";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        String userName = (String) req.getSession().getAttribute(USERNAME);
        String kickUser = (String) req.getAttribute(KICK_USERNAME);

        Message message = new Message(String.format(KICK_MESSAGE, userName, kickUser),
                TypeMessage.KICK, userName, new Date());
        messageDAO.addMessage(message);

        Message[] messages = messageDAO.getAllMessages();

        userDAO.removeUserByName(kickUser);
        String[] users = userDAO.getAllUserNamesExceptIn(userName);

        DynamicData dynamicData = new DynamicData(messages, users);

        String dynamicDataJson = mapper.writeValueAsString(dynamicData);
        resp.getWriter().write(dynamicDataJson);
    }
}
