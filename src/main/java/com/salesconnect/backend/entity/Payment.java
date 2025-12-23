package com.salesconnect.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentNumber;

    private LocalDateTime paymentDate;
    @Column(precision = 15, scale = 2)
    private BigDecimal amount;
    private String status;
    private String method;

    @ManyToOne
    @JoinColumn(name = "document_number")
    private Invoice invoice;
}
