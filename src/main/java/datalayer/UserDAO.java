package datalayer;

import datalayer.data.User;

public interface UserDAO {
    boolean isLogin(User user);
    boolean isLoginByName(String name);
    boolean isUserNameExists(String name);

    void saveUser(User user);
    void removeUserByName(String name);

    String[] getAllUserNamesExceptIn(String name);
}
