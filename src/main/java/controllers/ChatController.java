package controllers;

import chat.Command;
import chat.CommandType;
import com.fasterxml.jackson.core.JsonParseException;
import datalayer.DAOFactory;
import datalayer.MessageDAO;
import datalayer.StorageType;
import datalayer.UserDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "chat", urlPatterns = "/chat")
public class ChatController extends HttpServlet {
    private MessageDAO messageDAO;
    private UserDAO userDAO;

    private static final Logger logger = Logger.getLogger(ChatController.class);

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        userDAO = daoFactory.getUserDAO();
        messageDAO = daoFactory.getMessageDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");

        try{
            CommandType commandType = CommandType.getTypeCommandByRequest(req);

            logger.info("Type of command: " + commandType);
            Command command = CommandType.getCommandChat(commandType);

            logger.info("Execute of command: " + commandType);
            command.execute(req, resp, userDAO, messageDAO);
        } catch (IllegalArgumentException e){
            logger.error("Requested unknown command: " + e.toString());
        } catch (JsonParseException e){
            logger.error("Error Json Parse: " + e.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}

