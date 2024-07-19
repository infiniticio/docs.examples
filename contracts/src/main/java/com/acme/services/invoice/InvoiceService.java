package com.acme.services.invoice;

import com.acme.workflows.invoicing.User;
import io.infinitic.annotations.Name;

import java.time.LocalDateTime;

@Name(name = "InvoiceService")
public interface InvoiceService {
    Invoice create(User user, double amount, LocalDateTime start, LocalDateTime end);
}
