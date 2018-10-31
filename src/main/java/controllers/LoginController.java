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
    private static final String LOGIN_MESSAGE = "User \"%s\" is logging in chat";

    private MessageDAO messageDAO;

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        messageDAO = daoFactory.getMessageDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");

        logger.debug("Save \"" + userName + "\" in Session");
        req.getSession().setAttribute("username", userName);

        Message message = new Message(String.format(LOGIN_MESSAGE, userName),
                TypeMessage.LOGIN, userName, new Date());
        messageDAO.addMessage(message);

        logger.info("User: userName = \"" + userName + "\" is input in chat");
        resp.sendRedirect("/chat/index.html");
    }
}
