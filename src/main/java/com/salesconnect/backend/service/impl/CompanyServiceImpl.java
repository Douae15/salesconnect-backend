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
    public CompanyDTO registerCompany(CompanyDTO companyDTO) {
        // Créer l'entité Company
        Company company = Company.builder()
                .name(companyDTO.getName())
                .country(companyDTO.getCountry())
                .address(companyDTO.getAddress())
                .email(companyDTO.getEmail())
                .phone(companyDTO.getPhone())
                .industry(companyDTO.getIndustry())
                .country(companyDTO.getCountry())
                .build();

        // Sauvegarder l'entreprise
        company = companyRepository.save(company);

        // Créer l'admin de l'entreprise
        // On prend les données de l'admin depuis le UserDTO, et on encode le mot de passe
        UserDTO adminDTO = companyDTO.getUsersDTO().stream()
                .filter(user -> user.getRole() == Role.ADMIN_COMPANY)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin user is missing"));

        User admin = User.builder()
                .firstName(adminDTO.getFirstName())
                .lastName(adminDTO.getLastName())
                .email(adminDTO.getEmail())
                .phone(adminDTO.getPhone())
                .password(passwordEncoder.encode(adminDTO.getPassword())) // Encodage du mot de passe
                .role(Role.ADMIN_COMPANY) // Rôle Admin d'entreprise
                .company(company) // Associer l'admin à l'entreprise
                .build();

        // Sauvegarder l'admin
        userRepository.save(admin);

        // Mapper l'entité Company vers DTO pour la réponse
        CompanyDTO response = CompanyDTO.builder()
                .companyId(company.getCompanyId())
                .name(company.getName())
                .country(company.getCountry())
                .address(company.getAddress())
                .email(company.getEmail())
                .phone(company.getPhone())
                .industry(company.getIndustry())
                .build();

        return response;
    }


    @Override
    public CompanyDTO updateCompany(Long id, CompanyDTO companyDTO) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        if (existingCompany.isPresent()) {
            Company company = companyTransformer.toEntity(companyDTO);
            company.setCompanyId(id);
            Company updatedCompany = companyRepository.save(company);
            return companyTransformer.toDTO(updatedCompany);
        }
        return null;
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
