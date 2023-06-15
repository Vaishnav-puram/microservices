package com.example.userService.controller;

import com.example.userService.exceptions.ResourceNotFound;
import com.example.userService.model.User;
import com.example.userService.service.UserService;
import com.example.userService.service.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){

        User user1=userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }


    int retryCount=1;
    //@CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService",fallbackMethod ="ratingHotelFallback")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) throws ResourceNotFound {
        logger.info("retryCount"+retryCount);
        retryCount++;
        User user1=userService.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(user1);
    }

    //creating fallback method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String id,Exception ex){
        logger.info("Fallback is executed because service is down "+ex.getMessage());
        User user=User.builder().name("dummyName").email("dummy@gmail.com").uid("dummyID").build();
        return new ResponseEntity<>(user,HttpStatus.OK);

    }

    @GetMapping("/usersList")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList=userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }


}
