package com.acme.workflows.payment;

import com.acme.services.notification.BookingRequest;
import com.acme.workflows.invoicing.User;
import io.infinitic.annotations.Name;

@Name(name = "PaymentWorkflow")
public interface PaymentWorkflow {
    void getDeposit(BookingRequest request);

    void getPayment(User user, double amount);
}
