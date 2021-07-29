package example.booking;

import example.booking.tasks.carRental.CarRentalCart;
import example.booking.tasks.flight.FlightBookingCart;
import example.booking.tasks.hotel.HotelBookingCart;
import example.booking.workflows.BookingResult;
import example.booking.workflows.BookingWorkflow;
import io.infinitic.client.Deferred;
import io.infinitic.client.InfiniticClient;
import io.infinitic.pulsar.PulsarInfiniticClient;

public class Client {
    public static void main(String[] args) {
        // instantiate Infinitic client based on infinitic.yml config file
        InfiniticClient client = PulsarInfiniticClient.fromConfigFile("configs/infinitic.yml");

        int i = 0;
        while (i < 10) {
            // faking some carts
            CarRentalCart carRentalCart = new CarRentalCart();
            FlightBookingCart flightCart = new FlightBookingCart();
            HotelBookingCart hotelCart = new HotelBookingCart();

            // create a stub for BookingWorkflow
            BookingWorkflow bookingWorkflow = client.newWorkflow(BookingWorkflow.class);

            // dispatch workflow
            Deferred<BookingResult> deferred = client.async(
                    bookingWorkflow,
                    w -> w.book(carRentalCart, flightCart, hotelCart)
            );

            System.out.println("workflow " + BookingWorkflow.class.getName() + " " + deferred.getId() + " dispatched!");

            i++;
        }
    }
}