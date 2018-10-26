package datalayer.type.jvm;

import datalayer.DAOFactory;
import datalayer.MessageDAO;
import datalayer.UserDAO;

public class JvmDAOFactory extends DAOFactory {
    private static JvmDAOFactory jvmDAOFactory;

    public static synchronized JvmDAOFactory getInstance(){
        if (jvmDAOFactory == null){
            jvmDAOFactory = new JvmDAOFactory();
        }

        return jvmDAOFactory;
    }

    private JvmDAOFactory(){

    }

    @Override
    public MessageDAO getMessageDAO() {
        return JvmMessageDAO.getInstance();
    }

    @Override
    public UserDAO getUserDAO() {
        return JvmUserDAO.getInstance();
    }
}
