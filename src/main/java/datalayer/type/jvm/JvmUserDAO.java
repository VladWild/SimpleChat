package datalayer.type.jvm;

import datalayer.UserDAO;
import datalayer.data.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class JvmUserDAO implements UserDAO {
    private static JvmUserDAO jvmUserDAO;

    private Set<User> users = new HashSet<>();

    public static synchronized JvmUserDAO getInstance(){
        if (jvmUserDAO == null){
            jvmUserDAO = new JvmUserDAO();
        }

        return jvmUserDAO;
    }

    private JvmUserDAO(){

    }

    @Override
    public synchronized boolean isLogin(User user) {
        return users.contains(user);
    }

    @Override
    public synchronized boolean isUserNameExists(String name) {
        return users.stream()
                .map(User::getName)
                .collect(Collectors.toSet())
                .contains(name);
    }

    @Override
    public synchronized void saveUser(User user) {
        users.add(user);
    }

    @Override
    public synchronized void removeUserByName(String name) {
        users = users.stream()
                .filter(userName -> !userName.getName().equals(name))
                .collect(Collectors.toSet());
    }

    @Override
    public synchronized String[] getAllUserNamesExceptIn(String name) {
        return users.stream().map(User::getName)
                .filter(userName -> !userName.equals(name))
                .toArray(String[]::new);
    }
}

