package com.example.nullam.exceptions;

public class CompanyNotFound extends Exception{

    public CompanyNotFound(Long companyId){
        super(String.format("Company with id: %d not found", companyId));
    }
}
