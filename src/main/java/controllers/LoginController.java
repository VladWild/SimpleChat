package controllers;

import datalayer.DAOFactory;
import datalayer.MessageDAO;
import datalayer.StorageType;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.message.Message;
import datalayer.data.message.TypeMessage;
import org.apache.log4j.Logger;
import validation.Validation;
import validation.UserValidate;

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

    private static final String PAGE_CHAT = "/chat/index.html";
    private static final String PAGE_LOGIN = "/index.jsp";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private MessageDAO messageDAO;
    private UserDAO userDAO;

    private Validation userValidation;

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    public void init(){
        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        messageDAO = daoFactory.getMessageDAO();
        userDAO = daoFactory.getUserDAO();

        userValidation = new UserValidate();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //get dto
        if (userValidation.isValidate(req)){
            String userName = req.getParameter(USERNAME);
            String password = req.getParameter(PASSWORD);

            User user = new User(userName, password);

            logger.debug("Save user in DAO: " + user.toString());
            userDAO.saveUser(user);

            logger.debug("Save user in request: " + user.toString());
            req.getSession().setAttribute(USERNAME, user);

            logger.debug("Save message logging user: " + user.toString());
            Message message = new Message(String.format(LOGIN_MESSAGE, userName),
                    TypeMessage.LOGIN, userName, new Date());
            messageDAO.addMessage(message);

            logger.info("User is logging in chat: " + user.toString());
            resp.sendRedirect(PAGE_CHAT);
        } else {
            logger.info("User didn't enter the chat " + req.getParameter(USERNAME));
            req.getRequestDispatcher(PAGE_LOGIN).forward(req, resp);
        }
    }
}

