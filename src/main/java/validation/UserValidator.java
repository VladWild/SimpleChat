package validation;

import datalayer.DAOFactory;
import datalayer.StorageType;
import datalayer.UserDAO;
import datalayer.data.User;
import datalayer.data.loginerror.LoginError;
import datalayer.data.loginerror.TypeLoginError;
import dto.login.LoginData;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class UserValidator implements Validation {
    private static final String EMPTY_NAME_ERROR = "empty username";
    private static final String PASSWORD_EMPTY_ERROR = "empty password";
    private static final String PASSWORD_IS_NOT_VALIDATE = "password is not validate";

    private Set<LoginError> loginErrors;

    private UserDAO userDAO;

    private static final Logger logger = Logger.getLogger(UserValidator.class);

    public UserValidator(){
        loginErrors = new HashSet<>();

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
    public Set<LoginError> isValidate(Object o) {
        LoginData loginData = (LoginData) o;
        logger.debug("Get loginData: " + loginData.toString());

        logger.debug("Clear login errors");
        loginErrors.clear();

        String userName = loginData.getName();
        if (isEmpty(userName)){
            logger.debug("Username is empty");
            loginErrors.add(new LoginError(TypeLoginError.USERNAME_ERROR, EMPTY_NAME_ERROR));
        }
        logger.debug("Username is not empty: " + userName);

        String password = loginData.getPassword();
        if (isEmpty(password)){
            logger.debug("Password is empty");
            loginErrors.add(new LoginError(TypeLoginError.PASSWORD_ERROR, PASSWORD_EMPTY_ERROR));
        }
        logger.debug("Password is not empty: " + password);

        User user = new User(userName, password);

        if (isUserNameAndPasswordValidate(user)){
            logger.debug("Username and password is not validate");
            loginErrors.add(new LoginError(TypeLoginError.PASSWORD_ERROR, PASSWORD_IS_NOT_VALIDATE));
        }
        logger.debug("Username and password is validate: " + user.toString());

        return loginErrors;
    }
}


