package controllers.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.Login;
import datalayer.data.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/username")
public class UserName extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserName.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentUser = (String) req.getSession().getAttribute("username");

        String json = new ObjectMapper().writeValueAsString(currentUser);
        resp.getWriter().write(json);
    }
}
