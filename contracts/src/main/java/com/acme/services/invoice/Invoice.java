package com.acme.services.invoice;

import java.time.LocalDateTime;
import java.util.UUID;

public class Invoice {

    private String id;

    public Invoice(String userId, double amount, LocalDateTime start, LocalDateTime end) {
        this.id = UUID.randomUUID().toString();
    }

    // Needed for Json serialization
    public Invoice() { super(); }

    public String getId() { return id; }
}
