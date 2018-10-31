package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.dto.InitializationData;
import datalayer.data.message.Message;
import datalayer.data.message.TypeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class Login implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        String userName = (String) req.getSession().getAttribute(USERNAME);

        Message[] messages = messageDAO.getAllMessages();
        String[] users = userDAO.getAllUserNamesExceptIn(userName);

        InitializationData initializationData = new InitializationData(userName, messages, users);

        String initializationDataJson = mapper.writeValueAsString(initializationData);
        resp.getWriter().write(initializationDataJson);
    }
}
