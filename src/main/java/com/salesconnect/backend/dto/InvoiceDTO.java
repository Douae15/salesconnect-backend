package com.salesconnect.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Long documentNumber;
    private String invoiceNumber;
    private BigDecimal amount;
    private LocalDateTime dueDate;
    private String status;
    private OrderDTO orderDTO;
    private List<PaymentDTO> paymentsDTO;
}

