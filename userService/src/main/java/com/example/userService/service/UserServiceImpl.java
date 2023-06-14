package com.example.userService.service;

import com.example.userService.exceptions.ResourceNotFound;
import com.example.userService.model.User;
import com.example.userService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        String randomUserId=UUID.randomUUID().toString();
        user.setUid(randomUserId);
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(String uid) throws ResourceNotFound {
        return userRepo.findById(uid).orElseThrow(()->new ResourceNotFound("given id not found !!"+uid));
    }
}
