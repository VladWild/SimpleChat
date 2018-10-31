package controllers;

import datalayer.DAOFactory;
import datalayer.MessageDAO;
import datalayer.StorageType;
import datalayer.data.message.Message;
import datalayer.data.message.TypeMessage;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        logger.debug("Save \"" + username + "\" in Session");
        req.getSession().setAttribute("username", username);

        logger.info("User: userName = \"" + username + "\" is input in chat");
        resp.sendRedirect("/chat/index.html");
    }
}
