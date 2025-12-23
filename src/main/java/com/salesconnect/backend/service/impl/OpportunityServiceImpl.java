package com.salesconnect.backend.service.impl;

import com.salesconnect.backend.dto.OpportunityDTO;
import com.salesconnect.backend.dto.ProductDTO;
import com.salesconnect.backend.entity.Opportunity;
import com.salesconnect.backend.entity.Product;
import com.salesconnect.backend.repository.OpportunityRepository;
import com.salesconnect.backend.repository.ProductRepository;
import com.salesconnect.backend.service.OpportunityService;
import com.salesconnect.backend.transformer.OpportunityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OpportunityServiceImpl implements OpportunityService {

    @Autowired
    private OpportunityRepository opportunityRepository;
    @Autowired
    private ProductRepository productRepository;

    private final OpportunityTransformer opportunityTransformer = new OpportunityTransformer();

    @Override
    public List<OpportunityDTO> getAllOpportunities() {
        List<Opportunity> opportunities = opportunityRepository.findAll();
        return opportunityTransformer.toDTOList(opportunities);
    }

    @Override
    public OpportunityDTO getOpportunityById(Long id) {
        Opportunity opportunity = opportunityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Opportunity not found with id: " + id));
        return opportunityTransformer.toDTO(opportunity);
    }

    @Override
    public OpportunityDTO addOpportunity(OpportunityDTO opportunityDTO) {
        Opportunity opportunity = opportunityTransformer.toEntity(opportunityDTO);

        // Associer les produits à l'opportunité
        if (opportunityDTO.getProductsDTO() != null && !opportunityDTO.getProductsDTO().isEmpty()) {
            List<Product> products = new ArrayList<>();
            for (ProductDTO productDTO : opportunityDTO.getProductsDTO()) {
                Product product = productRepository.findById(productDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'ID : " + productDTO.getProductId()));

                // Ajouter l'opportunité au produit
                product.getOpportunities().add(opportunity);
                products.add(product);
            }
            // Ajouter les produits à l'opportunité
            opportunity.setProducts(products);
        }

        Opportunity savedOpportunity = opportunityRepository.save(opportunity);
        return opportunityTransformer.toDTO(savedOpportunity);
    }



    @Override
    public OpportunityDTO updateOpportunity(Long id, OpportunityDTO opportunityDTO) {
        Optional<Opportunity> existingOpportunity = opportunityRepository.findById(id);
        if (existingOpportunity.isPresent()) {
            existingOpportunity.get().setOpportunityId(id);
            Opportunity updatedOpportunity = opportunityRepository.save(existingOpportunity.get());
            return opportunityTransformer.toDTO(updatedOpportunity);
        }
        return null;
    }

    @Override
    public boolean deleteOpportunity(Long id) {
        if (opportunityRepository.existsById(id)) {
            opportunityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
