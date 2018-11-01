package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import dto.InitializationData;
import datalayer.data.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        User user = (User) req.getSession().getAttribute(USERNAME);

        Message[] messages = messageDAO.getAllMessages();
        String[] users = userDAO.getAllUserNamesExceptIn(user.getName());

        InitializationData initializationData = new InitializationData(user.getName(), messages, users);

        String initializationDataJson = mapper.writeValueAsString(initializationData);
        resp.getWriter().write(initializationDataJson);
    }
}

