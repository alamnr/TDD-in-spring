package com.example.test_driven_development.user;

import java.util.List;

public class UserService {
    
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(){
        return userDao.findAll();
    }

    public User getUserByUserName(String userName){
        return userDao.findByName(userName);
    }

    public void storeNewUser(User user){
        userDao.save(user);
    }


    
}
