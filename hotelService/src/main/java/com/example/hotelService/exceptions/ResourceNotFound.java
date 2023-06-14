package com.example.hotelService.exceptions;

public class ResourceNotFound extends Exception{
    public ResourceNotFound(){
        super("Resource not found!");
    }
    public ResourceNotFound(String message){
        super(message);
    }
}
