package com.example.hotelService.controller;

import com.example.hotelService.exceptions.ResourceNotFound;
import com.example.hotelService.model.Hotel;
import com.example.hotelService.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping("/createHotel")
    public ResponseEntity<Hotel> createUser(@RequestBody Hotel hotel){

        Hotel hotel1=hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel1);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/hotel/{id}")
    public ResponseEntity<Hotel> getUser(@PathVariable String id) throws ResourceNotFound {
        Hotel hotel=hotelService.getHotel(id);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')|| hasAuthority('Admin')")
    @GetMapping("/hotelsList")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> userList=hotelService.getAllHotels();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }



}
