package com.example.userService.service;

import com.example.userService.exceptions.ResourceNotFound;
import com.example.userService.external.service.HotelService;
import com.example.userService.model.Hotel;
import com.example.userService.model.Rating;
import com.example.userService.model.User;
import com.example.userService.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo userRepo;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    HotelService hotelService;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

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
        User user= userRepo.findById(uid).orElseThrow(()->new ResourceNotFound("given id not found !!"+uid));
        //fetching ratings for this user using ratings service
        Rating[] userRatings=restTemplate.getForObject("http://Rating-Service/ratings/userRatings/"+user.getUid(), Rating[].class);
        List<Rating> ratingArrayList=Arrays.stream(userRatings).toList();
        logger.info("user ratings-->"+ratingArrayList.get(0));
        List<Rating> ratingList=ratingArrayList.stream().map(
                rating -> {
                    //api call to hotel to get the hotel details
                    logger.info("hotel id from rating service -->"+rating.getHotelId());
                    //Hotel hotel=restTemplate.getForObject("http://Hotel-Service/hotels/hotel/"+rating.getHotelId(),Hotel.class);
                    Hotel hotel=hotelService.getHotel(rating.getHotelId());
                    logger.info(hotel.toString());
                    rating.setHotel(hotel);
                    return rating;
                }
        ).collect(Collectors.toList());
        user.setRatings(ratingArrayList);
        return user;
    }
}
