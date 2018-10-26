package datalayer;

public abstract class DAOFactory {
    public static DAOFactory getInstance(StorageType storageType) {
        return storageType.getDAOFactory();
    }

    public abstract MessageDAO getMessageDAO();

    public abstract UserDAO getUserDAO();
}
