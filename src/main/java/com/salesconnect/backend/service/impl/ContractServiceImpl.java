package com.salesconnect.backend.service.impl;

import com.salesconnect.backend.dto.ContractDTO;
import com.salesconnect.backend.entity.Contract;
import com.salesconnect.backend.entity.Opportunity;
import com.salesconnect.backend.repository.ContractRepository;
import com.salesconnect.backend.repository.OpportunityRepository;
import com.salesconnect.backend.service.ContractService;
import com.salesconnect.backend.transformer.ContractTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private OpportunityRepository opportunityRepository;
    private final ContractTransformer contractTransformer = new ContractTransformer();

    @Override
    public List<ContractDTO> getAllContracts() {
        return contractTransformer.toDTOList(contractRepository.findAll());
    }

    @Override
    public ContractDTO getContractById(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        return contract.map(contractTransformer::toDTO).orElse(null);
    }

    @Override
    public ContractDTO addContract(ContractDTO contractDTO) {
        Opportunity opportunity = opportunityRepository.findById(contractDTO.getOpportunityId())
                .orElseThrow(() -> new RuntimeException("Opportunité non trouvée"));

        Contract contract = new Contract();
        contract.setName(contractDTO.getName());
        contract.setContractNumber(contractDTO.getContractNumber());
        contract.setContractTerm(contractDTO.getContractTerm());
        contract.setOpportunity(opportunity);

        Contract savedContract = contractRepository.save(contract);
        return contractTransformer.toDTO(savedContract);
    }

    @Override
    public ContractDTO updateContract(Long id, ContractDTO contractDTO) {
        return null;
    }

    @Override
    public boolean deleteContract(Long id) {
        return false;
    }
}
