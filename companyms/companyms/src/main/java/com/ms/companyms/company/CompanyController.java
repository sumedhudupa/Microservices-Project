package com.ms.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id,@RequestBody Company updatedCompany){
        companyService.updateCompany(updatedCompany,id);
        return ResponseEntity.ok("done.");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCompany(@RequestBody Company newCompany){
        companyService.createCompany(newCompany);
        return ResponseEntity.ok("New company added successfully.");
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCompany(@PathVariable Long id){
        boolean confirmation = companyService.deleteCompany(id);
        if(confirmation == false){
            return new ResponseEntity<>("Company with this ID was not found.", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok("Company deleted successfully.");
    }
}
