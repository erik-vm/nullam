package com.example.nullam.exceptions;

public class PersonNotFound extends Exception {

    public PersonNotFound(Long personId){
        super(String.format("Person with id: %d not found", personId));
    }
}
