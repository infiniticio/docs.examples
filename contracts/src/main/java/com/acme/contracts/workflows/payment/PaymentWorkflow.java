package com.acme.contracts.workflows.payment;

import com.acme.contracts.services.notification.BookingRequest;
import com.acme.contracts.workflows.invoicing.User;
import io.infinitic.annotations.Name;

@Name(name = "PaymentWorkflow")
public interface PaymentWorkflow {
    void getDeposit(BookingRequest request);

    void getPayment(User user, double amount);
}
