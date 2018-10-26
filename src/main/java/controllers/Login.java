package controllers;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = "/chat")
public class Login extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Login.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");

        logger.debug("Save \"" + username + "\" in Session");
        req.getSession().setAttribute("username", username);

        logger.info("User: userName = \"" + username + "\" is input in chat");
        resp.sendRedirect("/chat/index.html");
    }
}
