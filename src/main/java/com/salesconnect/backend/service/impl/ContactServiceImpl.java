package com.salesconnect.backend.service.impl;

import com.salesconnect.backend.dto.CompanyDTO;
import com.salesconnect.backend.dto.ContactDTO;
import com.salesconnect.backend.dto.UserDTO;
import com.salesconnect.backend.entity.Company;
import com.salesconnect.backend.entity.Contact;
import com.salesconnect.backend.entity.User;
import com.salesconnect.backend.entity.enums.Role;
import com.salesconnect.backend.repository.CompanyRepository;
import com.salesconnect.backend.repository.ContactRepository;
import com.salesconnect.backend.service.ContactService;
import com.salesconnect.backend.transformer.ContactTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private CompanyRepository companyRepository;

    private final ContactTransformer contactTransformer = new ContactTransformer();

    @Override
    public List<ContactDTO> getAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return contactTransformer.toDTOList(contacts);
    }

    @Override
    public ContactDTO getContactById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
        return contactTransformer.toDTO(contact);
    }

    @Override
    public List<ContactDTO> getContactsByCompany(Long companyId) {
        List<Contact> contacts = contactRepository.findByCompanyCompanyId(companyId);
        return contactTransformer.toDTOList(contacts);
    }

    @Override
    public ContactDTO addContact(ContactDTO contactDTO) {
        // Récupérer l'entreprise associée
        Company company = companyRepository.findById(contactDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Entreprise non trouvée"));

        // Transformer le DTO en entité
        Contact contact = contactTransformer.toEntity(contactDTO);
        contact.setCompany(company); // Associer l'entreprise

        // Sauvegarder le contact
        Contact savedContact = contactRepository.save(contact);

        // Retourner le Contact sauvegardé sous forme de DTO
        return contactTransformer.toDTO(savedContact);
    }

    @Override
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        Optional<Contact> existingContact = contactRepository.findById(id);
        if (existingContact.isPresent()) {
            existingContact.get().setContactId(id);
            Contact updatedContact = contactRepository.save(existingContact.get());
            return contactTransformer.toDTO(updatedContact);
        }
        return null;
    }

    @Override
    public boolean deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
