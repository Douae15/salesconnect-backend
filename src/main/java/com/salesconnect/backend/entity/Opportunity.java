package com.salesconnect.backend.entity;

import com.salesconnect.backend.entity.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "opportunity")
public class Opportunity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opportunityId;

    private String name;
    private String stage;
    private double amount;
    private LocalDateTime closeDate;


    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @OneToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToMany(mappedBy = "opportunities", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> products = new ArrayList<>();


}

