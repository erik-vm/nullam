package com.example.nullam.exceptions;

public class PersonWithThisPersonalIdCodeAlreadyExists extends Exception{

    public PersonWithThisPersonalIdCodeAlreadyExists(String personalIdCode){
        super(String.format("Person with personal id number: %s already exists", personalIdCode));
    }
}
