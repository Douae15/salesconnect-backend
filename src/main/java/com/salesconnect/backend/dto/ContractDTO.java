package com.salesconnect.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {

    private Long contractId;

    private String name;
    private Long contractNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String contractTerm;
    private Long opportunityId;
    private List<OrderDTO> ordersDTO;
}

