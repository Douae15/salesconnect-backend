package com.salesconnect.backend.service.impl;

import com.salesconnect.backend.dto.CompanyDTO;
import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.entity.Company;
import com.salesconnect.backend.entity.User;
import com.salesconnect.backend.entity.enums.Role;
import com.salesconnect.backend.repository.CompanyRepository;
import com.salesconnect.backend.repository.UserRepository;
import com.salesconnect.backend.service.CompanyService;
import com.salesconnect.backend.transformer.CompanyTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final CompanyTransformer companyTransformer = new CompanyTransformer();

    @Override
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companyTransformer.toDTOList(companies);
    }

    @Override
    public CompanyDTO getCompanyById(Long id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.map(companyTransformer::toDTO).orElse(null);
    }

    @Override
    public CompanyDTO getCompanyForCompanyAdmin() {
        // Récupérer l'utilisateur connecté
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // Chercher l'utilisateur par email
        User adminUser = userRepository.findByEmail(userEmail);

        // Vérifier si c'est bien un admin d'entreprise
        if (adminUser.getRole() != Role.ADMIN_COMPANY) {
            throw new RuntimeException("Access denied: Not a company admin");
        }

        // Retourner les infos de l'entreprise associée
        Company company = adminUser.getCompany();
        return companyTransformer.toDTO(company);
    }

    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        if (companyDTO.getCompanyName() != null) {
            company.setName(companyDTO.getCompanyName());
        }
        if (companyDTO.getCompanyEmail() != null) {
            company.setEmail(companyDTO.getCompanyEmail());
        }
        if (companyDTO.getCompanyPhone() != null) {
            company.setPhone(companyDTO.getCompanyPhone());
        }
        if (companyDTO.getCompanyAddress() != null) {
            company.setAddress(companyDTO.getCompanyAddress());
        }
        if (companyDTO.getCompanyIndustry() != null) {
            company.setIndustry(companyDTO.getCompanyIndustry());
        }
        if (companyDTO.getCompanyCountry() != null) {
            company.setCountry(companyDTO.getCompanyCountry());
        }

        Company updatedCompany = companyRepository.save(company);
        return companyTransformer.toDTO(updatedCompany);
    }

    @Override
    public boolean deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
