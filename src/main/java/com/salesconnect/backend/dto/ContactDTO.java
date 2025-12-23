package com.salesconnect.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDTO {

    private Long contactId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Long companyId;
    private List<OpportunityDTO> opportunitiesDTO;
    private List<ActivityDTO> activitiesDTO;
}
