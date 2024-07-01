package com.example.test_driven_development.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {

    private final List<User> inMemoryUserList = new ArrayList<>();

  @PostConstruct
  public void init() {
    this.inMemoryUserList.add(new User("duke", "duke@spring.io"));
    this.inMemoryUserList.add(new User("mandy", "mandy@spring.io"));
  }

  public List<User> getAllUsers() {
    return inMemoryUserList;
  }

  public User getUserByUsername(String username) {
    return inMemoryUserList.stream()
        .filter(user -> user.name().equals(username))
        .findFirst()
        .orElseThrow(() -> new UserNotFoundException("Can't find this user *sad smiley*"));
  }

  public void storeNewUser(User user) {
    this.inMemoryUserList.add(user);
  }
    
    /*
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
    }   */    
}
