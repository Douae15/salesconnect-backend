package com.salesconnect.backend.transformer;

import com.salesconnect.backend.dto.InvoiceDTO;
import com.salesconnect.backend.dto.PaymentDTO;
import com.salesconnect.backend.entity.Contact;
import com.salesconnect.backend.entity.Invoice;
import com.salesconnect.backend.entity.Payment;

public class PaymentTransformer extends Transformer<Payment, PaymentDTO> {
    @Override
    public Payment toEntity(PaymentDTO paymentDTO) {
        if (paymentDTO == null)
            return null;
        else {
            Transformer<Invoice, InvoiceDTO> invoiceTransformer = new InvoiceTransformer();
            Payment payment = new Payment();
            payment.setPaymentNumber(paymentDTO.getPaymentNumber());
            payment.setPaymentDate(paymentDTO.getPaymentDate());
            payment.setAmount(paymentDTO.getAmount());
            payment.setMethod(paymentDTO.getMethod());
            payment.setStatus(paymentDTO.getStatus());
            if (paymentDTO.getInvoiceId() != null) {
                Invoice invoice = new Invoice();
                invoice.setDocumentNumber(paymentDTO.getInvoiceId());
                payment.setInvoice(invoice);
            } else {
                payment.setInvoice(null);
            }
            return payment;
        }
    }

    @Override
    public PaymentDTO toDTO(Payment payment) {
        if (payment == null)
            return null;
        else {
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentNumber(payment.getPaymentNumber());
            paymentDTO.setPaymentDate(payment.getPaymentDate());
            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setMethod(payment.getMethod());
            paymentDTO.setStatus(payment.getStatus());
            paymentDTO.setInvoiceId(payment.getInvoice().getDocumentNumber());
            return paymentDTO;
        }
    }
}
