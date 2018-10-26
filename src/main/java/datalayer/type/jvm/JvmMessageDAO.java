package datalayer.type.jvm;

import datalayer.MessageDAO;

class JvmMessageDAO implements MessageDAO {
    private static JvmMessageDAO jvmMessageDAO;

    public static synchronized JvmMessageDAO getInstance(){
        if (jvmMessageDAO == null){
            jvmMessageDAO = new JvmMessageDAO();
        }

        return jvmMessageDAO;
    }

    private JvmMessageDAO(){

    }
}
