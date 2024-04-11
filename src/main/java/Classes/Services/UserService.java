package Classes.Services;

import Classes.User.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> userList;

    public UserService() {
        userList = new ArrayList<>();
    }

    // Methods for managing users
    public void addUser(User user) {
        userList.add(user);
    }

    public List<User> getUserList() {
        return userList;
    }
}