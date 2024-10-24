package com.acme.contracts.services.metrics;

import com.acme.contracts.workflows.invoicing.User;
import io.infinitic.annotations.Name;

import java.time.LocalDateTime;

@Name(name = "MetricsService")
public interface MetricsService {
    double calculateAmount(User user, LocalDateTime start, LocalDateTime end);

    boolean isSubscribed(User user);
}
