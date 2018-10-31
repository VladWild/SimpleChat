package controllers;

import chat.Command;
import chat.CommandType;
import chat.parser.ParserData;
import chat.parser.SimpleParserData;
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
import java.util.Objects;

@WebServlet(name = "chat", urlPatterns = "/chat")
public class ChatController extends HttpServlet {
    private MessageDAO messageDAO;
    private UserDAO userDAO;

    private ParserData parserData;
    private ObjectMapper mapper;

    private static final Logger logger = Logger.getLogger(ChatController.class);

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        userDAO = daoFactory.getUserDAO();
        messageDAO = daoFactory.getMessageDAO();

        parserData = new SimpleParserData();
        mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //req.setCharacterEncoding("UTF-8");
        //resp.setContentType("text/html; charset=UTF-8");

        //System.out.println("Я в сервлете");
        //System.out.println(req.getRequestURL());
        //resp.sendRedirect(req.getContextPath() + "/index.jsp");
        //req.getRequestDispatcher("/chat/in.html").forward(req, resp);
        //resp.sendRedirect("/");

        try{
            parserData.parse(req);

            Command command = CommandType.getCommandChat(req);
            command.execute(req, resp, userDAO, messageDAO, mapper);
        } catch (IllegalArgumentException e){
            logger.error("Requested unknown command: " + e.toString());
        } catch (JsonParseException e){
            logger.error("Error Json Parse: " + e.toString());
        }
    }

    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getContextPath() + "/index.jsp");
        //resp.sendRedirect(req.getContextPath() + "/index.jsp");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }*/
}

