package example.booking;

import example.booking.services.carRental.CarRentalCart;
import example.booking.services.flight.FlightBookingCart;
import example.booking.services.hotel.HotelBookingCart;
import example.booking.workflows.BookingWorkflow;
import io.infinitic.clients.InfiniticClient;

public class Client {
    public static void main(String[] args) {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/configs/infinitic.yml")) {
            // create a stub for BookingWorkflow
            BookingWorkflow bookingWorkflow = client.newWorkflow(BookingWorkflow.class);

            int i = 0;
            while (i < 50) {
                String strI = String.valueOf(i);
                // faking some carts
                CarRentalCart carRentalCart = new CarRentalCart();
                FlightBookingCart flightCart = new FlightBookingCart();
                HotelBookingCart hotelCart = new HotelBookingCart();

                // dispatch workflow
                client.dispatchAsync(bookingWorkflow::book, carRentalCart, flightCart, hotelCart)
                        .thenApply( (deferred) ->  {
                            System.out.println("Workflow " + deferred.getId() + " (" + strI + ") dispatched!");

                            return null;
                        })
                        .exceptionally( (error) -> {
                            System.err.println("Failed to dispatch (" + strI + "): "  + error.toString());

                            return null;
                        });
                i++;
            }
        }
    }
}