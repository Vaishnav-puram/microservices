package com.example.hotelService.service;

import com.example.hotelService.exceptions.ResourceNotFound;
import com.example.hotelService.model.Hotel;
import com.example.hotelService.repo.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService{
    @Autowired
    HotelRepo hotelRepo;

    @Override
    public Hotel createHotel(Hotel hotel) {
        String randomHotelId= UUID.randomUUID().toString();
        hotel.setHid(randomHotelId);
        return hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel getHotel(String hId) throws ResourceNotFound {
        return hotelRepo.findById(hId).orElseThrow(()->new ResourceNotFound("given id not found !!"+hId));
    }
}
