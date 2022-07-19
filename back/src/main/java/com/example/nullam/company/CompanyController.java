package com.example.nullam.company;

import com.example.nullam.exceptions.CompanyNotFound;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    List<Company> showAllCompanies() throws Exception {
        return companyService.showAllCompanies();
    }

    @GetMapping("/find={id}")
    Company findCompanyById(@PathVariable("id") Long id) throws CompanyNotFound {
        return companyService.findCompanyById(id);
    }

    @PostMapping("/save")
    Company saveNewCompany(@RequestBody Company company) throws Exception {
        return companyService.saveCompany(company);
    }

    @PutMapping("update={id}")
    Company updateCompanyById(@PathVariable("id") Long id, @RequestBody Company company) throws Exception {
        return companyService.updateCompanyInfoByCompanyId(id, company);
    }

    @DeleteMapping("/delete={id}")
    void deleteCompanyById(@PathVariable("id") Long id) throws CompanyNotFound {
        companyService.deleteCompanyById(id);
    }
}
