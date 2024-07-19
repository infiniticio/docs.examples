package com.acme.workflows.payment.java;

import com.acme.services.notification.BookingRequest;
import com.acme.utils.AbstractWorkflow;
import com.acme.workflows.invoicing.User;
import com.acme.workflows.payment.PaymentWorkflow;

public class PaymentWorkflowImpl extends AbstractWorkflow implements PaymentWorkflow {
    @Override
    public void getDeposit(BookingRequest request) {
        log("Request " + request.getId() + ": Running GetDeposit");
    }

    @Override
    public void getPayment(User user, double amount) {
        log("Get payment " + amount + " from User " + user.getId());
    }
}
