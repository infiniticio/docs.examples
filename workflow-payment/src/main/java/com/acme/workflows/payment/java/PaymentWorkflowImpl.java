package com.acme.workflows.payment.java;

import com.acme.contracts.services.notification.BookingRequest;
import com.acme.common.AbstractWorkflow;
import com.acme.contracts.workflows.invoicing.User;
import com.acme.contracts.workflows.payment.PaymentWorkflow;

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
