package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.CompanyDTO;
import com.salesconnect.backend.dto.ContactDTO;
import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.entity.Company;
import com.salesconnect.backend.entity.Contact;
import com.salesconnect.backend.entity.User;

public class CompanyTransformer extends Transformer<Company, CompanyDTO> {
    @Override
    public Company toEntity(CompanyDTO companyDTO) {
        if (companyDTO == null)
            return null;
        else {
            Company company = new Company();
            company.setCompanyId(companyDTO.getCompanyId());
            company.setName(companyDTO.getCompanyName());
            company.setCountry(companyDTO.getCompanyCountry());
            company.setEmail(companyDTO.getCompanyEmail());
            company.setPhone(companyDTO.getCompanyPhone());
            company.setAddress(companyDTO.getCompanyAddress());
            company.setIndustry(companyDTO.getCompanyIndustry());
            if (companyDTO.getUserId() != null) {
                User user = new User();
                user.setUserId(companyDTO.getUserId());
                company.getUsers().add(user);
            } else {
                company.setUsers(null);
            }
            if (companyDTO.getContactId() != null) {
                Contact contact = new Contact();
                contact.setContactId(companyDTO.getContactId());
                company.getContacts().add(contact);
            } else {
                company.setContacts(null);
            }
            return company;
        }
    }

    @Override
    public CompanyDTO toDTO(Company company) {
        if (company == null)
            return null;
        else {
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setCompanyId(company.getCompanyId());
            companyDTO.setCompanyName(company.getName());
            companyDTO.setCompanyCountry(company.getCountry());
            companyDTO.setCompanyAddress(company.getAddress());
            companyDTO.setCompanyPhone(company.getPhone());
            companyDTO.setCompanyEmail(company.getEmail());
            companyDTO.setCompanyIndustry(company.getIndustry());
            if (company.getUsers() != null) {
                companyDTO.setUserId(company.getUsers().get(0).getUserId());
            }
            if (company.getContacts() != null && !company.getContacts().isEmpty()) {
                Contact contact = company.getContacts().get(0);
                companyDTO.setContactId(contact.getContactId());
            }

            return companyDTO;
        }
    }
}
