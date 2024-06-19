package example.booking.services.flight;

import java.util.UUID;

public class FlightBookingCart {
    private final UUID cartId = UUID.randomUUID();

    // code below is needed for serialization/deserialization

    public FlightBookingCart() { super(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightBookingCart that = (FlightBookingCart) o;
        return cartId.equals(that.cartId);
    }

    @Override
    public int hashCode() {
        return cartId.hashCode();
    }
}
