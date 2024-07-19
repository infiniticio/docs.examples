package com.acme.workflows.booking;

import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;
import com.acme.services.notification.BookingRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Random;

public class Start extends AbstractClient {

    public static final String bookingTag = "booking";

    public static void main(String[] args) {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
            // For all provided ids
            for (String customId : getCustomIds(args)) {
                // create a stub from BookingWorkflow interface, with a customId tag
                BookingWorkflow booking = client.newWorkflow(BookingWorkflow.class, Set.of(bookingTag, customId));
                // Dispatch booking::start with a dummy BookingRequest
                client.dispatchVoidAsync(booking::start, dummyBookingRequest(customId))
                        .thenApply(deferred -> printDispatched(customId, deferred.getId()))
                        .exceptionally(error -> printError(customId, error))
                        .join();
            }
        }
    }

    private static final List<String> names = Arrays.asList(
            "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Linda", "Michael",
            "Sarah", "Jacob", "Mohammed", "Samantha", "Jessica", "Andrew", "Daniel", "Emma",
            "Amanda", "Joshua", "Sophia", "Ethan", "Madison", "Olivia", "Anthony", "Isabella"
    );

    private static BookingRequest dummyBookingRequest(String id) {
        Random random = new Random();

        return new BookingRequest(
                id,
                names.get(random.nextInt(names.size())),
                names.get(random.nextInt(names.size()))
        );
    }
}
