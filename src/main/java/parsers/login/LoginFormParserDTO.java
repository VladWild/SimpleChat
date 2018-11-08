package parsers.login;

import dto.login.LoginData;
import org.apache.log4j.Logger;
import parsers.ParserDTO;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public class LoginFormParserDTO implements ParserDTO {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final Logger logger = Logger.getLogger(LoginFormParserDTO.class);

    @Override
    public Object parse(HttpServletRequest req) throws UnsupportedEncodingException {
        LoginData loginData = new LoginData();

        String userName = req.getParameter(USERNAME);
        logger.debug("Get username from request: " + userName);
        loginData.setName(userName);

        String password = req.getParameter(PASSWORD);
        logger.debug("Get password from request: " + password);
        loginData.setPassword(password);

        return loginData;
    }
}

