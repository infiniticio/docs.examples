package com.acme.services.metrics;

import com.acme.workflows.invoicing.User;
import io.infinitic.annotations.Name;

import java.time.LocalDateTime;

@Name(name = "MetricsService")
public interface MetricsService {
    double calculateAmount(User user, LocalDateTime start, LocalDateTime end);

    boolean isSubscribed(User user);
}
