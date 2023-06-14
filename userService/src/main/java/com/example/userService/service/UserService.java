package com.example.userService.service;

import com.example.userService.exceptions.ResourceNotFound;
import com.example.userService.model.User;

import java.util.List;

public interface UserService {

     User saveUser(User user);
     List<User> getAllUsers();
     User getUser(String uid) throws ResourceNotFound;
}
