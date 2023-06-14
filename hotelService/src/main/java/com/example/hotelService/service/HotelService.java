package com.example.hotelService.service;

import com.example.hotelService.exceptions.ResourceNotFound;
import com.example.hotelService.model.Hotel;

import java.util.List;


public interface HotelService {
    Hotel createHotel(Hotel hotel);
    List<Hotel> getAllHotels();
    Hotel getHotel(String hId) throws ResourceNotFound;

}
