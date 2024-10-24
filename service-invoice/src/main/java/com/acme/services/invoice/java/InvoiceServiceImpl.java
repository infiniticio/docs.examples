package com.acme.services.invoice.java;

import com.acme.contracts.services.invoice.Invoice;
import com.acme.contracts.services.invoice.InvoiceService;
import com.acme.common.AbstractService;
import com.acme.contracts.workflows.invoicing.User;

import java.time.LocalDateTime;

public class InvoiceServiceImpl extends AbstractService implements InvoiceService {

    @Override
    public Invoice create(User user, double amount, LocalDateTime start, LocalDateTime end) {
        log(String.format("Creating new Invoice for user (%s), amount (%s), start (%s), end (%s)", user.getId(), amount, start, end));
        return new Invoice(user.getId(), amount, start, end);
    }
}
