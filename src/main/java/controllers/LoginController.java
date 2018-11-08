package controllers;

import datalayer.DAOFactory;
import datalayer.MessageDAO;
import datalayer.StorageType;
import datalayer.UserDAO;
import datalayer.data.LoginError;
import datalayer.data.User;
import datalayer.data.massage.Message;
import datalayer.data.massage.TypeMessage;
import dto.login.LoginData;
import org.apache.log4j.Logger;
import parsers.ParserDTO;
import parsers.login.LoginFormParserDTO;
import validation.UserValidator;
import validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private static final String LOGIN_MESSAGE = "User \"%s\" is logging in chat";

    private static final String PAGE_CHAT = "/chat/index.html";
    private static final String PAGE_LOGIN = "/login.jsp";

    private static final String USERNAME = "username";

    private static ParserDTO loginFormParserDTO = new LoginFormParserDTO();

    private MessageDAO messageDAO;
    private UserDAO userDAO;

    private Validation userValidation;

    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Override
    public void init(){
        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        messageDAO = daoFactory.getMessageDAO();
        userDAO = daoFactory.getUserDAO();

        userValidation = new UserValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginData loginData = (LoginData) loginFormParserDTO.parse(req);

        List<LoginError> loginErrors = userValidation.isValidate(loginData).stream().map(obj -> (LoginError) obj).collect(Collectors.toList());

        if (loginErrors.isEmpty()){
            User user = new User(loginData.getName(), loginData.getPassword());

            logger.debug("Save user in DAO: " + user.toString());
            userDAO.saveUser(user);

            logger.debug("Save user in request: " + user.toString());
            req.getSession().setAttribute(USERNAME, user);

            logger.debug("Save message logging user: " + user.toString());
            Message message = new Message(String.format(LOGIN_MESSAGE, loginData.getName()),
                    TypeMessage.LOGIN, loginData.getPassword(), new Date());
            messageDAO.addMessage(message);

            logger.info("User is logging in chat: " + user.toString());
            resp.sendRedirect(PAGE_CHAT);
        } else {
            logger.info("User didn't enter the chat: " + loginData.getName());

            loginErrors.forEach(loginError -> req.setAttribute(loginError.getKey(), loginError.getValue()));
            logger.debug("Output error of login: " + loginErrors.stream().map(LoginError::toString).reduce("", (log, currentLoginError) -> log + currentLoginError));

            req.getRequestDispatcher(PAGE_LOGIN).forward(req, resp);
        }
    }
}

