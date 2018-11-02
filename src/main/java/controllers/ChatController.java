package controllers;

import chat.Command;
import chat.CommandType;
import chat.parsers.ParserDTO;
import chat.parsers.SendMessageParserDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private ParserDTO parserData;
    private ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(ChatController.class);

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        userDAO = daoFactory.getUserDAO();
        messageDAO = daoFactory.getMessageDAO();

        parserData = new SendMessageParserDTO();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        //отдаются в callback функцию фронта - ?
        //resp.sendRedirect("/");
        //req.getRequestDispatcher("/index.jsp").forward(req, resp);

        //resp.sendError(HttpServletResponse.SC_FORBIDDEN);

        try{
            CommandType commandType = CommandType.getTypeCommandByRequest(req);

            logger.info("Type of command: " + commandType);
            Command command = CommandType.getCommandChat(commandType);

            logger.info("Execute of command: " + commandType);
            command.execute(req, resp, userDAO, messageDAO, mapper);
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

