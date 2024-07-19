package com.acme.services.metrics.java;

import com.acme.services.metrics.MetricsService;
import com.acme.utils.AbstractService;
import com.acme.workflows.invoicing.User;

import java.time.Duration;
import java.time.LocalDateTime;

public class MetricsServiceImpl extends AbstractService implements MetricsService {
    @Override
    public double calculateAmount(User user, LocalDateTime start, LocalDateTime end) {
        double amount = Duration.between(start, end).toMillis() * Math.random();
        log("Amount calculated for user(" + user.getId() + "), from " + start + " to " + end + ": " + amount);
        return amount;
    }

    @Override
    public boolean isSubscribed(User user) {
        boolean isSubscribed = true;
        log("Checking if user (" + user.getId() + ") is still subscribed... " + isSubscribed);
        return isSubscribed;
    }
}
