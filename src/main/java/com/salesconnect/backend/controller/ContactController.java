package com.salesconnect.backend.controller;

import com.salesconnect.backend.dto.ContactDTO;
import com.salesconnect.backend.service.ContactService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
@Tag(name = "Contact", description = "The Contact API")
@Slf4j
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<ContactDTO>> getAllContacts() {
        log.info("Invoke Get All Contacts end point");
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
        ContactDTO contactDTO = contactService.getContactById(id);
        if (contactDTO != null) {
            return ResponseEntity.ok(contactDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ContactDTO>> getContactsByCompany(@PathVariable Long companyId) {
        List<ContactDTO> contacts = contactService.getContactsByCompany(companyId);
        return ResponseEntity.ok(contacts);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ContactDTO> addContact(@RequestBody ContactDTO contactDTO) {
        ContactDTO createdContact = contactService.addContact(contactDTO);
        return ResponseEntity.ok(createdContact);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);
        if (updatedContact != null) {
            return ResponseEntity.ok(updatedContact);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (contactService.deleteContact(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
