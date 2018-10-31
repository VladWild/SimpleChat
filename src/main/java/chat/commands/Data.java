package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.dto.DynamicData;
import datalayer.data.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Data implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        String userName = (String) req.getSession().getAttribute(USERNAME);

        Message[] messages = messageDAO.getAllMessages();
        String[] users = userDAO.getAllUserNamesExceptIn(userName);

        DynamicData dynamicData = new DynamicData(messages, users);

        String dynamicDataJson = mapper.writeValueAsString(dynamicData);
        resp.getWriter().write(dynamicDataJson);
    }
}
