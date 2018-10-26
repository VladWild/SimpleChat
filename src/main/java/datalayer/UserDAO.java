package datalayer;

import datalayer.data.User;

public interface UserDAO {
    boolean isLogin(User user);
    boolean isNameUsed(String name);
    void saveUser(User user);
}
