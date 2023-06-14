package com.example.userService.external.service;

import com.example.userService.model.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Hotel-Service")
public interface HotelService {

    @GetMapping("hotels/hotel/{hotelId}")
    Hotel getHotel(@PathVariable String hotelId);

}
