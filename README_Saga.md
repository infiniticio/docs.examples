# Saga Workflow

The workflow and its implementation are described [here](https://docs.infinitic.io/docs/introduction/examples#saga-workflow)

The `SagaWorkflow` implements a booking process combining a car rental, a flight, and a hotel reservation.
We require that all 3 bookings have to be successful together:
if any of them is unable to complete, we should cancel the other bookings that were successful.

This workflow combines 3 services:
- `CarRentalService` 
- `FlightBookingService`
- `HotelBookingService` 

Each of them have `book`, and `rollback` methods.
This last one is used to cancel a previously booked command.

## Deployment
To run `SagaWorkflow` instances, you need to start the following services and workflow:

### Java implementation

- Run at least one `CarRentalService` ([code](./service-car-rental/src/main/java/com/acme/services/carRental/java/CarRentalServiceImpl.java)) worker ([config](./service-car-rental/src/main/resources/java/worker.yml)):
    `./gradlew service-car-rental:run-java`

- Run at least one `FlightBookingService` ([code](./service-flight-booking/src/main/java/com/acme/services/flightBooking/java/FlightBookingServiceImpl.java)) worker ([config](./service-flight-booking/src/main/resources/java/worker.yml)):
    `./gradlew service-flight-booking:run-java`

- Run at least one `HotelBookingService` ([code](./service-hotel-booking/src/main/java/com/acme/services/hotelBooking/java/HotelBookingServiceImpl.java)) worker ([config](./service-hotel-booking/src/main/resources/java/worker.yml)):
    `./gradlew service-hotel-booking:run-java`

- Run at least one `SagaWorkflow` ([code](./workflow-saga/src/main/java/com/acme/workflows/saga/java/SagaWorkflowImpl.java))worker ([config](./workflow-saga/src/main/resources/java/worker.yml)):
    `./gradlew workflow-saga:run-java`
  

### Kotlin implementation

- Run at least one `CarRentalService` ([code](./service-car-rental/src/main/kotlin/com/acme/services/carRental/kotlin/CarRentalServiceImpl.kt)) worker ([config](./service-car-rental/src/main/resources/kotlin/worker.yml)):
    `./gradlew service-car-rental:run-kotlin`

- Run at least one `FlightBookingService` ([code](./service-flight-booking/src/main/kotlin/com/acme/services/flightBooking/kotlin/FlightBookingServiceImpl.kt)) worker ([config](./service-flight-booking/src/main/resources/kotlin/worker.yml)):
    `./gradlew service-flight-booking:run-kotlin`

- Run at least one `HotelBookingService` ([code](./service-hotel-booking/src/main/kotlin/com/acme/services/hotelBooking/kotlin/HotelBookingServiceImpl.kt)) worker ([config](./service-hotel-booking/src/main/resources/kotlin/worker.yml)):
    `./gradlew service-hotel-booking:run-kotlin`

- Run at least one `SagaWorkflow` ([code](./workflow-saga/src/main/kotlin/com/acme/workflows/saga/kotlin/SagaWorkflowImpl.kt)) worker ([config](./workflow-saga/src/main/resources/kotlin/worker.yml)):
    `./gradlew workflow-saga:run-kotlin`

### Starting Instances

To start ([code](./common/src/test/java/com/acme/clients/saga/Start.java))
5 `SagaWorkflow` instances, run: `./gradlew common:saga-start --args 5`.

The consoles should look like:

- From the `CarRentalService` workers:
    
    ```
    06:12:49.422 - 019286a7-5b20-78fd-9a92-de9a190683ee - CarRental - car rental started...
    06:12:49.422 - 019286a7-5b20-78fd-9a92-de9a190683eb - CarRental - car rental started...
    06:12:49.423 - 019286a7-5b20-78fd-9a92-de9a190683ed - CarRental - car rental started...
    06:12:49.423 - 019286a7-5b20-78fd-9a92-de9a190683ef - CarRental - car rental started...
    06:12:49.423 - 019286a7-5b20-78fd-9a92-de9a190683ec - CarRental - car rental started...
    06:12:49.427 - 019286a7-5b20-78fd-9a92-de9a190683ef - CarRental - car rental succeeded
    06:12:49.427 - 019286a7-5b20-78fd-9a92-de9a190683ee - CarRental - car rental succeeded
    06:12:49.427 - 019286a7-5b20-78fd-9a92-de9a190683ed - CarRental - car rental succeeded
    06:12:49.427 - 019286a7-5b20-78fd-9a92-de9a190683ec - CarRental - car rental request rejected
    06:12:49.427 - 019286a7-5b20-78fd-9a92-de9a190683eb - CarRental - car rental succeeded
    06:12:49.604 - 019286a7-5b20-78fd-9a92-de9a190683ed - CarRental - car rental request rolled back
    06:12:49.626 - 019286a7-5b20-78fd-9a92-de9a190683ee - CarRental - car rental request rolled back
    ```

- From the `FlightBookingService` workers:

    ```
    06:12:49.425 - 019286a7-5b20-78fd-9a92-de9a190683ec - FlightBooking - flight booking started...
    06:12:49.425 - 019286a7-5b20-78fd-9a92-de9a190683ed - FlightBooking - flight booking started...
    06:12:49.425 - 019286a7-5b20-78fd-9a92-de9a190683ef - FlightBooking - flight booking started...
    06:12:49.425 - 019286a7-5b20-78fd-9a92-de9a190683eb - FlightBooking - flight booking started...
    06:12:49.425 - 019286a7-5b20-78fd-9a92-de9a190683ee - FlightBooking - flight booking started...
    06:12:49.428 - 019286a7-5b20-78fd-9a92-de9a190683ef - FlightBooking - flight booking succeeded
    06:12:49.428 - 019286a7-5b20-78fd-9a92-de9a190683ed - FlightBooking - flight booking succeeded
    06:12:49.428 - 019286a7-5b20-78fd-9a92-de9a190683eb - FlightBooking - flight booking succeeded
    06:12:49.428 - 019286a7-5b20-78fd-9a92-de9a190683ec - FlightBooking - flight booking succeeded
    06:12:49.428 - 019286a7-5b20-78fd-9a92-de9a190683ee - FlightBooking - flight booking succeeded
    06:12:49.630 - 019286a7-5b20-78fd-9a92-de9a190683ec - FlightBooking - flight booking request rolled back
    06:12:49.654 - 019286a7-5b20-78fd-9a92-de9a190683ed - FlightBooking - flight booking request rolled back
    06:12:49.690 - 019286a7-5b20-78fd-9a92-de9a190683ee - FlightBooking - flight booking request rolled back
    ```

- From the `HotelBookingService` workers:

     ```
    06:12:49.414 - 019286a7-5b20-78fd-9a92-de9a190683ef - HotelBooking - hotel booking started...
    06:12:49.414 - 019286a7-5b20-78fd-9a92-de9a190683ec - HotelBooking - hotel booking started...
    06:12:49.414 - 019286a7-5b20-78fd-9a92-de9a190683ed - HotelBooking - hotel booking started...
    06:12:49.414 - 019286a7-5b20-78fd-9a92-de9a190683ee - HotelBooking - hotel booking started...
    06:12:49.414 - 019286a7-5b20-78fd-9a92-de9a190683eb - HotelBooking - hotel booking started...
    06:12:49.418 - 019286a7-5b20-78fd-9a92-de9a190683ef - HotelBooking - hotel booking succeeded
    06:12:49.418 - 019286a7-5b20-78fd-9a92-de9a190683ed - HotelBooking - hotel booking request rejected
    06:12:49.418 - 019286a7-5b20-78fd-9a92-de9a190683eb - HotelBooking - hotel booking succeeded
    06:12:49.418 - 019286a7-5b20-78fd-9a92-de9a190683ee - HotelBooking - hotel booking request rejected
    06:12:49.418 - 019286a7-5b20-78fd-9a92-de9a190683ec - HotelBooking - hotel booking succeeded
    06:12:49.696 - 019286a7-5b20-78fd-9a92-de9a190683ec - HotelBooking - hotel booking request rolled back
    ```

- From the `SagaWorkflow` workers:

    ```
    06:12:49.073 - 019286a7-5b20-78fd-9a92-de9a190683eb (saga) - SagaWorkflow - Saga started
    06:12:49.073 - 019286a7-5b20-78fd-9a92-de9a190683ec (saga) - SagaWorkflow - Saga started
    06:12:49.073 - 019286a7-5b20-78fd-9a92-de9a190683ed (saga) - SagaWorkflow - Saga started
    06:12:49.125 - 019286a7-5b20-78fd-9a92-de9a190683ee (saga) - SagaWorkflow - Saga started
    06:12:49.125 - 019286a7-5b20-78fd-9a92-de9a190683ef (saga) - SagaWorkflow - Saga started
    06:12:49.582 - 019286a7-5b20-78fd-9a92-de9a190683ef (saga) - SagaWorkflow - Saga succeeded
    06:12:49.582 - 019286a7-5b20-78fd-9a92-de9a190683eb (saga) - SagaWorkflow - Saga succeeded
    06:12:49.686 - 019286a7-5b20-78fd-9a92-de9a190683ed (saga) - SagaWorkflow - Saga rolled back
    06:12:49.715 - 019286a7-5b20-78fd-9a92-de9a190683ee (saga) - SagaWorkflow - Saga rolled back
    06:12:49.722 - 019286a7-5b20-78fd-9a92-de9a190683ec (saga) - SagaWorkflow - Saga rolled back
    ```

### Canceling Instances

To cancel ([code](./common/src/test/java/com/acme/clients/saga/CancelAll.java))
all `SagaWorkflow` instances, run: `./gradlew common:saga-cancel-all`.
