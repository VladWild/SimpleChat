package chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;
import datalayer.data.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    ObjectMapper mapper = new ObjectMapper();
    String USERNAME = "username";

    Logger logger = Logger.getLogger(Command.class);

    void execute(HttpServletRequest req, HttpServletResponse resp,
                 UserDAO userDAO, MessageDAO messageDAO) throws IOException;

    default User getUserFromSession(HttpServletRequest req){
        User user = (User) req.getSession().getAttribute(USERNAME);
        logger.debug("Get user from current session: " + user.toString());
        return user;
    }

    default void sendData(HttpServletResponse resp, Object data) throws IOException {
        String dynamicDataJson = mapper.writeValueAsString(data);
        logger.debug("Data in JSON format for output: " + data);
        resp.getWriter().write(dynamicDataJson);
    }
}


