package datalayer;

import datalayer.type.jvm.JvmDAOFactory;

public enum StorageType {
    JVM {
        @Override
        DAOFactory getDAOFactory() {
            return JvmDAOFactory.getInstance();
        }
    };

    abstract DAOFactory getDAOFactory();
}
