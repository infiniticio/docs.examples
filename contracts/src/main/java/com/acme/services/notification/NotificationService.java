package com.acme.services.notification;

import com.acme.services.invoice.Invoice;
import com.acme.workflows.invoicing.User;
import io.infinitic.annotations.Name;

@Name(name = "NotificationService")
public interface NotificationService {

    void notifyHostOfRequest(BookingRequest request, int attempt);

    void notifyHostOfRequestExpiration(BookingRequest request);

    void notifyTravelerOfRequestExpiration(BookingRequest request);

    void notifyHostOfPaymentSuccess(BookingRequest request);

    void notifyTravelerOfBookingSuccess(BookingRequest request);

    void notifyTravelerOfBookingDenial(BookingRequest request);

    void sendInvoice(User user, Invoice invoice);
}