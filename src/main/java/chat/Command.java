package chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import datalayer.MessageDAO;
import datalayer.UserDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Command {
    String USERNAME = "username";

    void execute(HttpServletRequest req, HttpServletResponse resp,
                 UserDAO userDAO, MessageDAO messageDAO, ObjectMapper mapper) throws IOException;
}
