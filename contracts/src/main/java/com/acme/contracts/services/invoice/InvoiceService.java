package com.acme.contracts.services.invoice;

import com.acme.contracts.workflows.invoicing.User;
import io.infinitic.annotations.Name;

import java.time.LocalDateTime;

@Name(name = "InvoiceService")
public interface InvoiceService {
    Invoice create(User user, double amount, LocalDateTime start, LocalDateTime end);
}
