package validation;

import datalayer.DAOFactory;
import datalayer.StorageType;
import datalayer.UserDAO;
import datalayer.data.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UserValidate implements Validation {
    private static final String EMPTY_NAME_ERROR = "empty username";
    private static final String EMPTY_PASSWORD_ERROR = "empty password";
    private static final String PASSWORD_IS_NOT_VALIDATE = "password is not validate";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String ATTR_USERNAME = "userNameError";
    private static final String ATTR_PASSWORD = "passwordError";

    private static final Logger logger = Logger.getLogger(UserValidate.class);

    private UserDAO userDAO;

    public UserValidate(){
        DAOFactory daoFactory = DAOFactory.getInstance(StorageType.JVM);
        userDAO = daoFactory.getUserDAO();
    }

    private boolean isEmpty(String userName){
        return userName.isEmpty();
    }

    private boolean isUserNameAndPasswordValidate(User user){
        if (userDAO.isLogin(user)){
            return false;
        } else {
            if (userDAO.isUserNameExists(user.getName())){
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean isValidate(Object o) {
        HttpServletRequest request = (HttpServletRequest) o;

        String userName = request.getParameter(USERNAME);
        if (isEmpty(userName)){
            logger.debug("Username is empty");
            request.setAttribute(ATTR_USERNAME, EMPTY_NAME_ERROR);
            return false;
        }
        logger.debug("Username is not empty: " + userName);

        String password = request.getParameter(PASSWORD);
        if (isEmpty(password)){
            logger.debug("Password is empty");
            request.setAttribute(ATTR_PASSWORD, EMPTY_PASSWORD_ERROR);
            return false;
        }
        logger.debug("Password is not empty: " + password);

        User user = new User(userName, password);

        if (isUserNameAndPasswordValidate(user)){
            logger.debug("Username and password is not validate");
            request.setAttribute(ATTR_PASSWORD, PASSWORD_IS_NOT_VALIDATE);
            return false;
        }
        logger.debug("Username and password is validate: " + user.toString());

        return true;
    }
}


