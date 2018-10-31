package filters.login.validate;

import datalayer.DAOFactory;
import datalayer.StorageType;
import datalayer.UserDAO;
import datalayer.data.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class ValidateUser implements Filter {
    private static final String PASSWORD_VALIDATE_ERROR = "password is not validate";

    private UserDAO userDAO;

    private static final Logger logger = Logger.getLogger(ValidateUser.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("init of filter \"Validate User\"");

        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        userDAO = daoFactory.getUserDAO();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        String userName =  req.getParameter("username");
        String password =  req.getParameter("password");

        User user = new User(userName, password);

        //если пользователь залогинен
        if (userDAO.isLogin(user)){
            //надо позволить ему войти
            logger.debug("Change to login controller");
            filterChain.doFilter(req, resp);
        } else {
            //проверить введеный userName на наличие
            if (userDAO.isUsernameExists(user.getName())){
                //выводим ошибку
                logger.debug("Save in request attribute error of empty password");
                req.setAttribute("passwordError", PASSWORD_VALIDATE_ERROR);

                logger.debug("Output error of empty empty in view: " + PASSWORD_VALIDATE_ERROR);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            } else {
                //создаем нового пользователя и позволяем ему войти
                logger.debug("Save user" + userName + " in DAO");
                userDAO.saveUser(user);

                logger.debug("Change to login controller");
                filterChain.doFilter(req, resp);
            }
        }
    }
}
