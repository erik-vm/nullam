package com.example.nullam.exceptions;

public class EventNotFound extends Exception{
    public EventNotFound(Long eventId){
        super(String.format("Event with id: %d not found", eventId));
    }
}
