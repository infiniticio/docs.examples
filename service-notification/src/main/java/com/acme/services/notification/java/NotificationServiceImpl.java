package com.acme.services.notification.java;


import com.acme.contracts.services.invoice.Invoice;
import com.acme.contracts.services.notification.BookingRequest;
import com.acme.contracts.services.notification.NotificationService;
import com.acme.common.AbstractService;
import com.acme.contracts.workflows.invoicing.User;

public class NotificationServiceImpl extends AbstractService implements NotificationService {
    @Override
    public void notifyHostOfRequest(BookingRequest request, int attempt) {
        log("Request " + request.getId() + ": Notifying Host " + request.getHost() + " of a new request from " + request.getTraveler() + " (attempt " + attempt + ")");
    }

    @Override
    public void notifyHostOfRequestExpiration(BookingRequest request) {
        log("Request " + request.getId() + ": Notifying Host " + request.getHost() + " of the expiration of the request");
    }

    @Override
    public void notifyTravelerOfRequestExpiration(BookingRequest request) {
        log("Request " + request.getId() + ": Notifying Traveler " + request.getTraveler() + " of the expiration her request");
    }

    @Override
    public void notifyHostOfPaymentSuccess(BookingRequest request) {
        log("Request " + request.getId() + ": Notifying Host " + request.getHost() + " of a successful payment");
    }

    @Override
    public void notifyTravelerOfBookingSuccess(BookingRequest request) {
        log("Request " + request.getId() + ": Notifying Traveler " + request.getTraveler() + " of the success of her booking");
    }

    @Override
    public void notifyTravelerOfBookingDenial(BookingRequest request) {
        log("Request " + request.getId() + ": Notifying Traveler " + request.getTraveler() + " of the denial of her booking");
    }

    @Override
    public void sendInvoice(User user, Invoice invoice) {
        log("Send Invoice " + invoice.getId() + " to user " + user.getId());
    }
}