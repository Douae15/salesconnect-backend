package com.salesconnect.backend.service;

import com.salesconnect.backend.dto.CompanyDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    List<CompanyDTO> getAllCompanies();
    CompanyDTO getCompanyById(Long id);
    CompanyDTO getCompanyForCompanyAdmin();
    CompanyDTO updateCompany(Long id, CompanyDTO companyDTO);
    boolean deleteCompany(Long id);

}
