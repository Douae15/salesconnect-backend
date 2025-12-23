package com.salesconnect.backend.service;

import com.salesconnect.backend.dto.CompanyDTO;
import com.salesconnect.backend.dto.ContactDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactService {
    List<ContactDTO> getAllContacts();
    ContactDTO getContactById(Long id);
    List<ContactDTO> getContactsByCompany(Long companyId);
    ContactDTO addContact(ContactDTO contactDTO);
    ContactDTO updateContact(Long id, ContactDTO contactDTO);
    boolean deleteContact(Long id);
}
