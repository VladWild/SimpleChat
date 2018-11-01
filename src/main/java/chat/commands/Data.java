package chat.commands;

import chat.Command;
import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import dto.DynamicData;
import datalayer.data.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class Data implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp, UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException {
        User user = (User) req.getSession().getAttribute(USERNAME);

        Message[] messages = messageDAO.getAllMessages();
        String[] users = userDAO.getAllUserNamesExceptIn(user.getName());

        // - ?
        System.out.println("     -----------       " + user.getName());
        Arrays.stream(users).forEach (e -> System.out.println(e) );
        // - ?

        DynamicData dynamicData = new DynamicData(messages, users);

        String dynamicDataJson = mapper.writeValueAsString(dynamicData);
        resp.getWriter().write(dynamicDataJson);
    }
}
