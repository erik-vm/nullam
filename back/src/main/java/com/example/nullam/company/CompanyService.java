package com.example.nullam.company;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final Pattern companyRegCodePattern = Pattern.compile("\\d{8}");

    List<Company> showAllCompanies() {
        if (companyRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return companyRepository.findAll();
    }

    public Company findCompanyById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return companyOptional.get();
    }

    public Company saveCompany(Company company) {
        if (!doesCompanyExistGetByRegCodeAndName(company.getName(), company.getCompanyRegNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        validateRegCode(company.getCompanyRegNumber());
        return companyRepository.save(company);
    }

    void deleteCompanyById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        companyRepository.delete(companyOptional.get());
    }

    Company updateCompanyInfoByCompanyId(Long id, Company updatedCompany) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Company existingCompany = companyOptional.get();
        existingCompany.setName(updatedCompany.getName());
        existingCompany.setDescription(updatedCompany.getDescription());
        existingCompany.setPaymentMethod(updatedCompany.getPaymentMethod());

        return companyRepository.save(existingCompany);
    }

    private boolean doesCompanyExistGetByRegCodeAndName(String name, String regCode) {
        List<Company> companyList = new ArrayList<>();
        for (Company company : companyRepository.findAll()) {
            if (company.getName().equals(name) && company.getCompanyRegNumber().equals(regCode)) {
                companyList.add(company);
            }
        }
        return companyList.isEmpty();
    }

    private void validateRegCode(String companyRegCode) {
        if (!companyRegCodePattern.matcher(companyRegCode).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
