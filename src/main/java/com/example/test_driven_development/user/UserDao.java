package com.example.test_driven_development.user;

import org.springframework.data.repository.ListCrudRepository;


public interface UserDao extends ListCrudRepository<User, Long>  {

    User findByName(String name);

}
