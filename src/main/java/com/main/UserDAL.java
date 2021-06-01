package com.main;

import java.util.List;

public interface UserDAL {
    List<User> getAllUsers();
    void addNewUser(User user);
    void deleteAll();
    List<User> getSpecificUsers(Integer occupation, Integer age, String gender);
}
