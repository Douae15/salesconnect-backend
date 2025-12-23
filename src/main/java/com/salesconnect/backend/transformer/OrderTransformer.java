package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.*;
import com.salesconnect.backend.entity.*;

public class OrderTransformer extends Transformer<Order, OrderDTO>{
    @Override
    public Order toEntity(OrderDTO orderDTO) {
        if (orderDTO == null)
            return null;
        else {
            Transformer<Company, CompanyDTO> companyTransformer = new CompanyTransformer();
            Transformer<Contract, ContractDTO> contractTransformer = new ContractTransformer();
            Transformer<Invoice, InvoiceDTO> invoicesTransformer = new InvoiceTransformer();
            Order order = new Order();
            order.setOrderNumber(orderDTO.getOrderNumber());
            order.setName(orderDTO.getName());
            order.setOrderDate(orderDTO.getOrderDate());
            order.setDeliveryDate(orderDTO.getDeliveryDate());
            order.setStatus(orderDTO.getStatus());
            if (orderDTO.getCompanyId() != null) {
                Company company = new Company();
                company.setCompanyId(orderDTO.getCompanyId());
                order.setCompany(company);
            } else {
                order.setCompany(null);
            }
            if (orderDTO.getContractId() != null) {
                Contract contract = new Contract();
                contract.setContractId(orderDTO.getContractId());
                order.setContract(contract);
            } else {
                order.setContract(null);
            }
            order.setInvoices(invoicesTransformer.toEntityList(orderDTO.getInvoicesDTO()));
            return order;
        }
    }

    @Override
    public OrderDTO toDTO(Order order) {
        if (order == null)
            return null;
        else {
            Transformer<Company, CompanyDTO> companyTransformer = new CompanyTransformer();
            Transformer<Contract, ContractDTO> contractTransformer = new ContractTransformer();
            Transformer<Invoice, InvoiceDTO> invoicesTransformer = new InvoiceTransformer();
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderNumber(order.getOrderNumber());
            orderDTO.setName(order.getName());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setDeliveryDate(order.getDeliveryDate());
            orderDTO.setStatus(order.getStatus());
            orderDTO.setCompanyId(order.getCompany().getCompanyId());
            orderDTO.setContractId(order.getContract().getContractId());
            orderDTO.setInvoicesDTO(invoicesTransformer.toDTOList(order.getInvoices()));
            return orderDTO;
        }
    }
}
