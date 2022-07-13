package com.example.nullam.company;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    List<Company> showAllCompanies() {
        return companyService.showAllCompanies();
    }

    @GetMapping("/find={id}")
    Company findCompanyById(@PathVariable("id") Long id){
       return companyService.findCompanyById(id);
    }

    @PostMapping("/save")
    Company saveNewCompany(@RequestBody Company company){
        return companyService.saveCompany(company);
    }

    @PutMapping("update={id}")
    Company updateCompanyById(@PathVariable("id") Long id, @RequestBody Company company){
        return companyService.updateCompanyInfoByCompanyId(id, company);
    }

    @DeleteMapping("/delete={id}")
    void deleteCompanyById(@PathVariable("id") Long id){
        companyService.deleteCompanyById(id);
    }
}
