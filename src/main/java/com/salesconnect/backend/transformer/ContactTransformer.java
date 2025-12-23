package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.*;
import com.salesconnect.backend.entity.*;

public class ContactTransformer extends Transformer<Contact, ContactDTO>{
    @Override
    public Contact toEntity(ContactDTO contactDTO) {
        if (contactDTO==null)
            return null;
        else{
            Transformer<Activity, ActivityDTO> activityTransformer = new ActivityTransformer();
            Transformer<Opportunity, OpportunityDTO> opportunityTransformer = new OpportunityTransformer();
            Contact contact = new Contact();
            contact.setContactId(contactDTO.getContactId());
            contact.setName(contactDTO.getName());
            contact.setEmail(contactDTO.getEmail());
            contact.setAddress(contactDTO.getAddress());
            contact.setPhone(contactDTO.getPhone());
            if (contactDTO.getCompanyId() != null) {
                Company company = new Company();
                company.setCompanyId(contactDTO.getCompanyId());
                company.setName(contactDTO.getName());
                contact.setCompany(company);
            }
            contact.setActivities(activityTransformer.toEntityList(contactDTO.getActivitiesDTO()));
            contact.setOpportunities(opportunityTransformer.toEntityList(contactDTO.getOpportunitiesDTO()));

            return contact;
        }
    }

    @Override
    public ContactDTO toDTO(Contact contact) {
        if (contact==null)
            return null;
        else{
            Transformer<Activity, ActivityDTO> activityTransformer = new ActivityTransformer();
            Transformer<Opportunity, OpportunityDTO> opportunityTransformer = new OpportunityTransformer();
            ContactDTO contactDTO = new ContactDTO();
            contactDTO.setContactId(contact.getContactId());
            contactDTO.setName(contact.getName());
            contactDTO.setEmail(contact.getEmail());
            contactDTO.setAddress(contact.getAddress());
            contactDTO.setPhone(contact.getPhone());
            if (contact.getCompany() != null) {
                CompanyDTO companyDTO = new CompanyDTO();
                companyDTO.setCompanyId(contact.getCompany().getCompanyId());
                companyDTO.setCompanyName(contact.getCompany().getName());
                contactDTO.setCompanyId(companyDTO.getCompanyId());
            }
            contactDTO.setActivitiesDTO(activityTransformer.toDTOList(contact.getActivities()));
            contactDTO.setOpportunitiesDTO(opportunityTransformer.toDTOList(contact.getOpportunities()));

            return contactDTO;
        }
    }
}
