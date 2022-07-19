package com.example.nullam.company;

import com.example.nullam.exceptions.CompanyNotFound;
import com.example.nullam.exceptions.CompanyWithThisRegCodeAlreadyExists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final Pattern companyRegCodePattern = Pattern.compile("\\d{8}");

    List<Company> showAllCompanies() throws Exception {
        if (companyRepository.findAll().isEmpty()) {
            throw new Exception("No companies in database.");
        }
        return companyRepository.findAll();
    }

    public Company findCompanyById(Long id) throws CompanyNotFound {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            throw new CompanyNotFound(id);
        }
        return companyOptional.get();
    }

    Company saveCompany(Company company) throws Exception {
        if (!doesCompanyExistGetByRegCodeAndName(company.getName(), company.getCompanyRegNumber())) {
            throw new CompanyWithThisRegCodeAlreadyExists(company.getCompanyRegNumber());
        }
        validateRegCode(company.getCompanyRegNumber());
        return companyRepository.save(company);
    }

    void deleteCompanyById(Long id) throws CompanyNotFound {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            throw new CompanyNotFound(id);
        }
        companyRepository.delete(companyOptional.get());
    }

    Company updateCompanyInfoByCompanyId(Long id, Company updatedCompany) throws Exception {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            throw new CompanyNotFound(id);
        }
        Company existingCompany = companyOptional.get();
        if (updatedCompany.getId() != null || updatedCompany.getCompanyRegNumber() != null) {
            throw new Exception("Changing values of id and company reg code is prohibited!");
        }
        if (updatedCompany.getName() != null) {
            existingCompany.setName(updatedCompany.getName());
        }
        if (updatedCompany.getDescription() != null) {
            existingCompany.setDescription(updatedCompany.getDescription());
        }
        if (updatedCompany.getPaymentMethod() != null) {
            existingCompany.setPaymentMethod(updatedCompany.getPaymentMethod());
        }
        return companyRepository.save(existingCompany);
    }

    public void updateCompanyEventList(Long id, Company updatedCompany) throws Exception {
        Company existingCompany = findCompanyById(id);
        if (!updatedCompany.getCompanyRegNumber().equals(existingCompany.getCompanyRegNumber()) &&
                !updatedCompany.getId().equals(existingCompany.getId())){
            throw new Exception("Changing values of id and company reg code is prohibited!");
        }
        companyRepository.save(existingCompany);
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

    private void validateRegCode(String companyRegCode) throws Exception {
        if (!companyRegCodePattern.matcher(companyRegCode).matches()) {
            throw new Exception(String.format("%s is not valid company registration code.", companyRegCode));
        }
    }
}
