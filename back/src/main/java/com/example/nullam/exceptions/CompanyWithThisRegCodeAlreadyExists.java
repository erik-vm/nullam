package com.example.nullam.exceptions;

public class CompanyWithThisRegCodeAlreadyExists extends Exception{

    public CompanyWithThisRegCodeAlreadyExists(String regCode){
        super(String.format("Company with reg code number: %s already exists", regCode));
    }
}
