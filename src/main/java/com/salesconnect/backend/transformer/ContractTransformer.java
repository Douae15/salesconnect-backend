package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.*;
import com.salesconnect.backend.entity.*;

public class ContractTransformer extends Transformer<Contract, ContractDTO>{
    @Override
    public Contract toEntity(ContractDTO contractDTO) {
        if (contractDTO == null)
            return null;
        else {
            Transformer<Opportunity, OpportunityDTO> opportunityTransformer = new OpportunityTransformer();
            Transformer<Order, OrderDTO> ordersTransformer = new OrderTransformer();
            Contract contract = new Contract();
            contract.setContractId(contractDTO.getContractId());
            contract.setContractNumber(contractDTO.getContractNumber());
            contract.setName(contractDTO.getName());
            contract.setContractTerm(contractDTO.getContractTerm());
            contract.setStartDate(contractDTO.getStartDate());
            contract.setEndDate(contractDTO.getEndDate());
            if (contractDTO.getOpportunityId() != null) {
                Opportunity opportunity = new Opportunity();
                opportunity.setOpportunityId(contractDTO.getOpportunityId());
                contract.setOpportunity(opportunity);
            } else {
                contract.setOpportunity(null);
            }
            contract.setOrders(ordersTransformer.toEntityList(contractDTO.getOrdersDTO()));
            return contract;
        }
    }

    @Override
    public ContractDTO toDTO(Contract contract) {
        if (contract == null)
            return null;
        else {
            Transformer<Opportunity, OpportunityDTO> opportunityTransformer = new OpportunityTransformer();
            Transformer<Order, OrderDTO> ordersTransformer = new OrderTransformer();
            ContractDTO contractDTO = new ContractDTO();
            contractDTO.setContractId(contract.getContractId());
            contractDTO.setContractNumber(contract.getContractNumber());
            contractDTO.setName(contract.getName());
            contractDTO.setContractTerm(contract.getContractTerm());
            contractDTO.setStartDate(contract.getStartDate());
            contractDTO.setEndDate(contract.getEndDate());
            contractDTO.setOpportunityId(contract.getOpportunity().getOpportunityId());
            contractDTO.setOrdersDTO(ordersTransformer.toDTOList(contract.getOrders()));
            return contractDTO;
        }
    }
}
