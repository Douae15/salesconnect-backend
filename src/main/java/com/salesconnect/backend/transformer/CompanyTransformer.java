package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.CompanyDTO;
import com.salesconnect.backend.dto.ContactDTO;
import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.entity.Company;
import com.salesconnect.backend.entity.Contact;
import com.salesconnect.backend.entity.User;

public class CompanyTransformer extends Transformer<Company, CompanyDTO>{
    @Override
    public Company toEntity(CompanyDTO companyDTO) {
        if (companyDTO==null)
            return null;
        else{
            Transformer<User, UserDTO> userTransformer = new UserTransformer();
            Transformer<Contact, ContactDTO> contactTransformer = new ContactTransformer();
            Company company = new Company();
            company.setCompanyId(companyDTO.getCompanyId());
            company.setName(companyDTO.getName());
            company.setEmail(companyDTO.getEmail());
            company.setAddress(companyDTO.getAddress());
            company.setIndustry(companyDTO.getIndustry());
            company.setUsers(userTransformer.toEntityList(companyDTO.getUsersDTO()));
            company.setContacts(contactTransformer.toEntityList(companyDTO.getContactsDTO()));
            return company;
        }
    }

    @Override
    public CompanyDTO toDTO(Company company) {
        if (company==null)
            return null;
        else{
            Transformer<User, UserDTO> userTransformer = new UserTransformer();
            Transformer<Contact, ContactDTO> contactTransformer = new ContactTransformer();
            CompanyDTO companyDTO = new CompanyDTO();
            companyDTO.setCompanyId(company.getCompanyId());
            companyDTO.setName(company.getName());
            companyDTO.setEmail(company.getEmail());
            companyDTO.setAddress(company.getAddress());
            companyDTO.setPhone(company.getPhone());
            companyDTO.setIndustry(company.getIndustry());
            companyDTO.setUsersDTO(userTransformer.toDTOList(company.getUsers()));
            companyDTO.setContactsDTO(contactTransformer.toDTOList(company.getContacts()));
            return companyDTO;
        }
    }
}
