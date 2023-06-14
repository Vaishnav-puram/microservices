package com.example.userService.controller;

import com.example.userService.exceptions.ResourceNotFound;
import com.example.userService.model.User;
import com.example.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User user1=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) throws ResourceNotFound {
        User user1=userService.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(user1);
    }

    @GetMapping("/usersList")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList=userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }


}
