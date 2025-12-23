package com.salesconnect.backend.repository;

import com.salesconnect.backend.entity.Contact;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByCompanyCompanyId(Long companyId);
}

