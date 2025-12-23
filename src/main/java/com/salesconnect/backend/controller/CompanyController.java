package com.salesconnect.backend.controller;


import com.salesconnect.backend.dto.CompanyDTO;
import com.salesconnect.backend.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Company", description = "The Company API")
@Slf4j
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        log.info("Invoke Get All Companies end point");
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long id) {
        CompanyDTO companyDTO = companyService.getCompanyById(id);
        if (companyDTO != null) {
            return ResponseEntity.ok(companyDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/company-infos")
    public ResponseEntity<CompanyDTO> getMyCompany() {
        log.info("Fetching company info for current admin");
        CompanyDTO companyDTO = companyService.getCompanyForCompanyAdmin();
        return ResponseEntity.ok(companyDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        CompanyDTO updatedCompany = companyService.updateCompany(id, companyDTO);
        if (updatedCompany != null) {
            return ResponseEntity.ok(updatedCompany);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        if (companyService.deleteCompany(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

