# examples

This repository contains Java and Kotlin implementations of the examples described in the Infinitic documentation:

- https://docs.infinitic.io/docs/introduction/hello-world
- https://docs.infinitic.io/docs/introduction/examples  

## Prerequisites

Before running the examples, ensure you have [Docker](https://docs.docker.com/engine/install/) installed on your system.

- Clone this repository to your local machine.
- Navigate to the project root folder.
- Run  `docker compose up` to start local Pulsar and Redis instances:

You can also run those examples on a different Pulsar cluster or database, just update the `infinitic.yml`([Java](./examples-java/src/main/resources/configs/infinitic.yml),
[Kotlin](./examples-kotlin/src/main/resources/configs/infinitic.yml)) file
[accordingly](https://docs.infinitic.io/docs/workflows/workers).

Note: To avoid mixing messages, the Java and Kotlin implementations run on different Pulsar namespaces.

## Hello World 

The `HelloWorkflow` ([Java](./workflow-hello-java/src/main/java/com/acme/workflows/hello/java/HelloWorkflowImpl.java),
[Kotlin](./workflow-hello-kotlin/src/main/kotlin/com/acme/workflows/hello/kotlin/HelloWorkflowImpl.kt))
illustrates how to implement a simple workflow with two sequential tasks from a `HelloService`
([Java](./service-hello-java/src/main/java/com/acme/services/hello/java/HelloServiceImpl.java),
[Kotlin](./service-hello-kotlin/src/main/kotlin/com/acme/services/hello/kotlin/HelloServiceImpl.kt))

It takes a `name` string as input and return `"Hello $name!"` using the following tasks:

- `sayHello` task: Takes a `name` string as input and returns `"Hello $name"`
- `addEnthusiasm` task: Takes a `str` string as input and returns `"$str!"`

The workflow is described in https://docs.infinitic.io/docs/introduction/hello-world.

### Deployment
To be able to run `HelloWorkflow` instances:

- Run a few `HelloService` workers

  - in Java ([config](./service-hello-java/src/main/resources/hello.yml),
    [code](./service-hello-java/src/main/java/com/acme/services/hello/java/Worker.java)):
    `./gradlew service-hello-java:run`

  - and/or in Kotlin ([config](./service-hello-kotlin/src/main/resources/hello.yml),
    [code](./service-hello-kotlin/src/main/kotlin/com/acme/services/hello/kotlin/Worker.kt)):
    `./gradlew service-hello-kotlin:run`

- Run a few `HelloWorkflow` workers

  - in Java ([config](./workflow-hello-java/src/main/resources/hello.yml),
    [code](./workflow-hello-java/src/main/java/com/acme/workflows/hello/java/Worker.java)):
    `./gradlew workflow-hello-java:run`
  - and/or in Kotlin ([config](./workflow-hello-kotlin/src/main/resources/hello.yml),
    [code](./workflow-hello-kotlin/src/main/kotlin/com/acme/workflows/hello/kotlin/Worker.kt)):
    `./gradlew workflow-hello-kotlin:run`

### Starting Instances

To start ([code](./contracts/src/test/java/com/acme/workflows/hello/Start.java))
10 `HelloWorkflow` instances, run: `./gradlew contracts:hello-start --args 10`.

The consoles should look like:

- From the `HelloService` workers:

```
06:26:45.970 - 0190b737-c6b6-7cf3-a817-9b3708410601 - HelloService - sayHello("0")
06:26:45.970 - 0190b737-c9fc-738a-a61f-fc8e7ed13a95 - HelloService - sayHello("5")
06:26:45.970 - 0190b737-ca19-73df-b730-d6be87301d2d - HelloService - sayHello("8")
06:26:45.970 - 0190b737-ca10-7bd1-87b7-903f3f78ac32 - HelloService - sayHello("7")
06:26:45.970 - 0190b737-c9ea-7c73-ac6a-78191bdf9615 - HelloService - sayHello("2")
06:26:45.970 - 0190b737-c9f6-71bc-8d3b-44237c19404d - HelloService - sayHello("4")
06:26:45.970 - 0190b737-c9f0-7011-8f1e-caccabb83527 - HelloService - sayHello("3")
06:26:45.970 - 0190b737-ca1f-700e-ad78-62cf8ec94a48 - HelloService - sayHello("9")
06:26:45.970 - 0190b737-ca0a-7857-9564-d2cde06d4721 - HelloService - sayHello("6")
06:26:45.970 - 0190b737-c9dd-7c78-a177-2eb476a52b80 - HelloService - sayHello("1")
06:26:46.128 - 0190b737-ca10-7bd1-87b7-903f3f78ac32 - HelloService - addEnthusiasm("Hello 7")
06:26:46.137 - 0190b737-c9ea-7c73-ac6a-78191bdf9615 - HelloService - addEnthusiasm("Hello 2")
06:26:46.137 - 0190b737-c9f6-71bc-8d3b-44237c19404d - HelloService - addEnthusiasm("Hello 4")
06:26:46.137 - 0190b737-c9fc-738a-a61f-fc8e7ed13a95 - HelloService - addEnthusiasm("Hello 5")
06:26:46.137 - 0190b737-c6b6-7cf3-a817-9b3708410601 - HelloService - addEnthusiasm("Hello 0")
06:26:46.147 - 0190b737-c9dd-7c78-a177-2eb476a52b80 - HelloService - addEnthusiasm("Hello 1")
06:26:46.147 - 0190b737-ca19-73df-b730-d6be87301d2d - HelloService - addEnthusiasm("Hello 8")
06:26:46.147 - 0190b737-ca1f-700e-ad78-62cf8ec94a48 - HelloService - addEnthusiasm("Hello 9")
06:26:46.158 - 0190b737-ca0a-7857-9564-d2cde06d4721 - HelloService - addEnthusiasm("Hello 6")
06:26:46.165 - 0190b737-c9f0-7011-8f1e-caccabb83527 - HelloService - addEnthusiasm("Hello 3")
```

- From the `HelloWorkflow` workers:

```
06:26:46.172 - 0190b737-ca10-7bd1-87b7-903f3f78ac32 (helloWorld) - HelloWorkflow - Hello 7!
06:26:46.172 - 0190b737-c6b6-7cf3-a817-9b3708410601 (helloWorld) - HelloWorkflow - Hello 0!
06:26:46.172 - 0190b737-c9f6-71bc-8d3b-44237c19404d (helloWorld) - HelloWorkflow - Hello 4!
06:26:46.183 - 0190b737-c9fc-738a-a61f-fc8e7ed13a95 (helloWorld) - HelloWorkflow - Hello 5!
06:26:46.183 - 0190b737-c9ea-7c73-ac6a-78191bdf9615 (helloWorld) - HelloWorkflow - Hello 2!
06:26:46.191 - 0190b737-ca1f-700e-ad78-62cf8ec94a48 (helloWorld) - HelloWorkflow - Hello 9!
06:26:46.191 - 0190b737-ca19-73df-b730-d6be87301d2d (helloWorld) - HelloWorkflow - Hello 8!
06:26:46.199 - 0190b737-c9dd-7c78-a177-2eb476a52b80 (helloWorld) - HelloWorkflow - Hello 1!
06:26:46.212 - 0190b737-ca0a-7857-9564-d2cde06d4721 (helloWorld) - HelloWorkflow - Hello 6!
06:26:46.221 - 0190b737-c9f0-7011-8f1e-caccabb83527 (helloWorld) - HelloWorkflow - Hello 3!
```

### Canceling

To cancel ([code](./contracts/src/test/java/com/acme/workflows/hello/Cancel.java))
all `HelloWorkflow` instances, run: `./gradlew contracts:hello-cancel`.

## Saga Workflow

The `SagaWorkflow` ([Java](./workflow-saga-java/src/main/java/com/acme/workflows/saga/java/SagaWorkflowImpl.java),
[Kotlin](./workflow-saga-kotlin/src/main/kotlin/com/acme/workflows/saga/kotlin/SagaWorkflowImpl.kt))
implements a booking process combining a car rental, a flight, and a hotel reservation.
We require that all 3 bookings have to be successful together:
if any of them fails, we should cancel the other bookings that were successful.

This workflow combines 3 services:
- `CarRentalService` ([Java](./service-car-rental-java/src/main/java/com/acme/services/carRental/java/CarRentalServiceImpl.java),
  [Kotlin](./service-car-rental-kotlin/src/main/kotlin/com/acme/services/carRental/kotlin/CarRentalServiceImpl.kt))
- `FlightBookingService` ([Java](./service-flight-booking-java/src/main/java/com/acme/services/flightBooking/java/FlightBookingServiceImpl.java),
  [Kotlin](./service-flight-booking-kotlin/src/main/kotlin/com/acme/services/flightBooking/kotlin/FlightBookingServiceImpl.kt))
- `HotelBookingService` ([Java](./service-hotel-booking-java/src/main/java/com/acme/services/hotelBooking/java/HotelBookingServiceImpl.java),
  [Kotlin](./service-hotel-booking-kotlin/src/main/kotlin/com/acme/services/hotelBooking/kotlin/HotelBookingServiceImpl.kt))

Each of them have `book`, and `cancel` methods.
This last one is used to cancel a previously booked command.

The workflow implementation is described in https://docs.infinitic.io/docs/introduction/examples#bookings-and-saga

### Deployment
To be able to run `SagaWorkflow` instances:

- Run a few `CarRentalService` workers

  - in Java ([config](./service-car-rental-java/src/main/resources/carRental.yml),
    [code](./service-car-rental-java/src/main/java/com/acme/services/carRental/java/Worker.java)):
    `./gradlew service-car-rental-java:run`

  - and/or in Kotlin ([config](./service-car-rental-kotlin/src/main/resources/carRental.yml),
    [code](./service-car-rental-kotlin/src/main/kotlin/com/acme/services/carRental/kotlin/Worker.kt)):
    `./gradlew service-car-rental-kotlin:run`

- Run a few `FlightBookingService` workers

  - in Java ([config](./service-flight-booking-java/src/main/resources/flightBooking.yml),
    [code](./service-flight-booking-java/src/main/java/com/acme/services/flightBooking/java/Worker.java)):
    `./gradlew service-flight-booking-java:run`

  - and/or in Kotlin ([config](./service-flight-booking-kotlin/src/main/resources/flightBooking.yml),
    [code](./service-flight-booking-kotlin/src/main/kotlin/com/acme/services/flightBooking/kotlin/Worker.kt)):
    `./gradlew service-flight-booking-kotlin:run`

- Run a few `HotelBookingService` workers

  - in Java ([config](./service-hotel-booking-java/src/main/resources/hotelBooking.yml),
    [code](./service-hotel-booking-java/src/main/java/com/acme/services/hotelBooking/java/Worker.java)):
    `./gradlew service-hotel-booking-java:run`

  - and/or in Kotlin ([config](./service-hotel-booking-kotlin/src/main/resources/hotelBooking.yml),
    [code](./service-hotel-booking-kotlin/src/main/kotlin/com/acme/services/hotelBooking/kotlin/Worker.kt)):
    `./gradlew service-hotel-booking-kotlin:run`

- Run a few `SagaWorkflow` workers

  - in Java ([config](./workflow-saga-java/src/main/resources/saga.yml),
    [code](./workflow-saga-java/src/main/java/com/acme/workflows/saga/java/Worker.java)):
    `./gradlew workflow-saga-java:run`
  - and/or in Kotlin ([config](./workflow-saga-kotlin/src/main/resources/saga.yml),
    [code](./workflow-saga-kotlin/src/main/kotlin/com/acme/workflows/saga/kotlin/Worker.kt)):
    `./gradlew workflow-saga-kotlin:run`

### Starting Instances

To start ([code](./contracts/src/test/java/com/acme/workflows/saga/Start.java))
5 `SagaWorkflow` instances, run: `./gradlew contracts:saga-start --args 5`.

The consoles should look like:

- From the `CarRentalService` workers:

  ```
  07:13:52.745 - 0190b762-ec17-79f2-96be-3fd49aa1e61d - CarRental - car rental started...
  07:13:52.747 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 - CarRental - car rental started...
  07:13:52.765 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 - CarRental - car rental started...
  07:13:52.778 - 0190b762-ef45-7d3d-b782-540eaf5b4104 - CarRental - car rental started...
  07:13:52.788 - 0190b762-ef3f-7307-849b-f0250b047865 - CarRental - car rental started...
  07:13:54.045 - 0190b762-ec17-79f2-96be-3fd49aa1e61d - CarRental - car rental succeeded
  07:13:55.124 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 - CarRental - car rental succeeded
  07:13:56.214 - 0190b762-ef45-7d3d-b782-540eaf5b4104 - CarRental - car rental succeeded
  07:13:56.786 - 0190b762-ef3f-7307-849b-f0250b047865 - CarRental - car rental succeeded
  07:13:57.632 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 - CarRental - car rental canceled
  07:13:57.666 - 0190b762-ef3f-7307-849b-f0250b047865 - CarRental - car rental canceled
  07:13:57.725 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 - CarRental - car rental failed
  ```

- From the `FlightBookingService` workers:

  ```
  07:13:52.747 - 0190b762-ec17-79f2-96be-3fd49aa1e61d - FlightBooking - flight booking started...
  07:13:52.748 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 - FlightBooking - flight booking started...
  07:13:52.768 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 - FlightBooking - flight booking started...
  07:13:52.779 - 0190b762-ef45-7d3d-b782-540eaf5b4104 - FlightBooking - flight booking started...
  07:13:52.788 - 0190b762-ef3f-7307-849b-f0250b047865 - FlightBooking - flight booking started...
  07:13:54.528 - 0190b762-ef45-7d3d-b782-540eaf5b4104 - FlightBooking - flight booking succeeded
  07:13:55.105 - 0190b762-ef3f-7307-849b-f0250b047865 - FlightBooking - flight booking succeeded
  07:13:56.212 - 0190b762-ec17-79f2-96be-3fd49aa1e61d - FlightBooking - flight booking succeeded
  07:13:57.316 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 - FlightBooking - flight booking failed
  07:13:57.424 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 - FlightBooking - flight booking failed
  07:13:57.716 - 0190b762-ef3f-7307-849b-f0250b047865 - FlightBooking - flight booking canceled
  ```

- From the `HotelBookingService` workers:

  ```
  07:13:52.748 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 - HotelBooking - hotel booking started...
  07:13:52.748 - 0190b762-ec17-79f2-96be-3fd49aa1e61d - HotelBooking - hotel booking started...
  07:13:52.769 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 - HotelBooking - hotel booking started...
  07:13:52.778 - 0190b762-ef45-7d3d-b782-540eaf5b4104 - HotelBooking - hotel booking started...
  07:13:52.787 - 0190b762-ef3f-7307-849b-f0250b047865 - HotelBooking - hotel booking started...
  07:13:52.979 - 0190b762-ef45-7d3d-b782-540eaf5b4104 - HotelBooking - hotel booking succeeded
  07:13:54.631 - 0190b762-ec17-79f2-96be-3fd49aa1e61d - HotelBooking - hotel booking succeeded
  07:13:55.049 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 - HotelBooking - hotel booking succeeded
  07:13:57.562 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 - HotelBooking - hotel booking failed
  07:13:57.617 - 0190b762-ef3f-7307-849b-f0250b047865 - HotelBooking - hotel booking failed
  07:13:57.785 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 - HotelBooking - hotel booking canceled
  ```

- From the `SagaWorkflow` workers:

  ```
  07:13:52.713 - 0190b762-ec17-79f2-96be-3fd49aa1e61d (saga) - SagaWorkflow - Saga started
  07:13:52.717 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 (saga) - SagaWorkflow - Saga started
  07:13:52.731 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 (saga) - SagaWorkflow - Saga started
  07:13:52.737 - 0190b762-ef45-7d3d-b782-540eaf5b4104 (saga) - SagaWorkflow - Saga started
  07:13:52.756 - 0190b762-ef3f-7307-849b-f0250b047865 (saga) - SagaWorkflow - Saga started
  07:13:56.236 - 0190b762-ec17-79f2-96be-3fd49aa1e61d (saga) - SagaWorkflow - Saga succeeded
  07:13:56.236 - 0190b762-ef45-7d3d-b782-540eaf5b4104 (saga) - SagaWorkflow - Saga succeeded
  07:13:57.657 - 0190b762-ef31-7cd3-8bcb-3b90f07a17f0 (saga) - SagaWorkflow - Saga failed
  07:13:57.750 - 0190b762-ef3f-7307-849b-f0250b047865 (saga) - SagaWorkflow - Saga failed
  07:13:57.813 - 0190b762-ef39-7cd5-83fd-aafad931f8a0 (saga) - SagaWorkflow - Saga failed
  ```

### Canceling Instances

To cancel ([code](./contracts/src/test/java/com/acme/workflows/saga/Cancel.java))
all `SagaWorkflow` instances, run: `./gradlew contracts:saga-cancel`.

## Loyalty

The `LoyaltyWorkflow`([Java](./workflow-loyalty-java/src/main/java/com/acme/workflows/loyalty/java/LoyaltyWorkflowImpl.java),
[Kotlin](./workflow-loyalty-kotlin/src/main/kotlin/com/acme/workflows/loyalty/kotlin/LoyaltyWorkflowImpl.kt))
illustrates how to use the properties of a workflow to store a state.

A `LoyaltyWorlkflow` instance has:

- a public `points` properties
- a `start` method. Once started, `points` will be incremented every 10 seconds
- an `addBonus` method with a BonusEvent object as parameter. When a bonus event occurs,
`points` will be incremented by a value depending on the type of event.

The workflow implementation is described in https://docs.infinitic.io/docs/introduction/examples#loyalty-program

### Deployment
To be able to run `LoyaltyWorlkflow` instances:

- Run a few `LoyaltyWorlkflow` workers

  - in Java ([config](./workflow-loyalty-java/src/main/resources/loyalty.yml),
    [code](./workflow-loyalty-java/src/main/java/com/acme/workflows/loyalty/java/Worker.java)):
    `./gradlew workflow-loyalty-java:run`
  - and/or in Kotlin ([config](./workflow-loyalty-kotlin/src/main/resources/loyalty.yml),
    [code](./workflow-loyalty-kotlin/src/main/kotlin/com/acme/workflows/loyalty/kotlin/Worker.kt)):
    `./gradlew workflow-loyalty-kotlin:run`

### Starting Instances

To start ([code](./contracts/src/test/java/com/acme/workflows/loyalty/Start.java))
a `LoyaltyWorlkflow` instance, run: `./gradlew contracts:loyalty-start --args '42'`.
Here "42" is an arbitrary string that identifies the instance.

We can call the `addBonus` method on this running instance to illustrate how to add bonus points
when a user does specific actions. To send ([code](./contracts/src/test/java/com/acme/workflows/loyalty/Send.java))
a `BonusEvent` to this instance:

- for a `ORDER_COMPLETED`, run: `./gradlew contracts:loyalty-send --args 'ORDER_COMPLETED 42'`.
- for a `FORM_COMPLETED`, run: `./gradlew contracts:loyalty-send --args 'FORM_COMPLETED 42'`.
- for a `REGISTRATION_COMPLETED`, run: `./gradlew contracts:loyalty-send --args 'REGISTRATION_COMPLETED 42'`.

To get the current value of `points`: `./gradlew contracts:loyalty-get --args '42'`.

The consoles should look like:

```
07:44:34.069 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 0
07:44:43.965 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 1
07:44:53.922 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 2
07:45:03.933 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 3
07:45:09.142 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - received ORDER_COMPLETED - new points = 503
07:45:13.914 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 504
07:45:23.937 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 505
07:45:33.923 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 506
07:45:37.261 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - received FORM_COMPLETED - new points = 706
07:45:43.921 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 707
07:45:53.935 - 0190b77f-03b4-7592-b293-954837743f8a (customId:42) - LoyaltyWorkflow - points = 708
```

### Canceling

To cancel ([code](./contracts/src/test/java/com/acme/workflows/loyalty/Cancel.java))
the instance: `./gradlew  contracts:loyalty-cancel --args '42'`.

## Booking

The `BookingWorkflow`([Java](./workflow-booking-java/src/main/java/com/acme/workflows/booking/java/BookingWorkflowImpl.java),
[Kotlin](./workflow-booking-kotlin/src/main/kotlin/com/acme/workflows/booking/kotlin/BookingWorkflowImpl.kt))
implements a communication process during a location booking, that includes:

- the `NotificationService` ([Java](./service-notification-java/src/main/java/com/acme/services/notification/java/NotificationServiceImpl.java),
  [Kotlin](./service-notification-kotlin/src/main/kotlin/com/acme/services/notification/kotlin/NotificationServiceImpl.kt))
- the `PaymentWorkflow` ([Java](./workflow-payment-java/src/main/java/com/acme/workflows/payment/java/PaymentWorkflowImpl.java),
  [Kotlin](./workflow-payment-kotlin/src/main/kotlin/com/acme/workflows/payment/kotlin/PaymentWorkflowImpl.kt))

The workflow is described in https://docs.infinitic.io/docs/introduction/examples#location-booking.

### Deployment
To be able to run `BookingWorkflow` instances:

- Run a few `NotificationService` workers 

  - in Java ([config](./service-notification-java/src/main/resources/notification.yml),
  [code](./service-notification-java/src/main/java/com/acme/services/notification/java/Worker.java)):
  `./gradlew service-notification-java:run`
  
  - and/or in Kotlin ([config](./service-notification-kotlin/src/main/resources/notification.yml),
  [code](./service-notification-kotlin/src/main/kotlin/com/acme/services/notification/kotlin/Worker.kt)):
  `./gradlew service-notification-kotlin:run`

- Run a few `BookingWorkflow` workers

  - in Java ([config](./workflow-booking-java/src/main/resources/booking.yml),
    [code](./workflow-booking-java/src/main/java/com/acme/workflows/booking/java/Worker.java)):
    `./gradlew workflow-booking-java:run`
  - and/or in Kotlin ([config](./workflow-booking-kotlin/src/main/resources/booking.yml),
    [code](./workflow-booking-kotlin/src/main/kotlin/com/acme/workflows/booking/kotlin/Worker.kt)): 
     `./gradlew workflow-booking-kotlin:run`

- Run a few `PaymentWorkflow` workers

  - in Java ([config](./workflow-payment-java/src/main/resources/payment.yml),
    [code](./workflow-payment-java/src/main/java/com/acme/workflows/payment/java/Worker.java)):
    `./gradlew workflow-payment-java:run`
  - and/or in Kotlin ([config](./workflow-payment-kotlin/src/main/resources/payment.yml),
    [code](./workflow-payment-kotlin/src/main/kotlin/com/acme/workflows/payment/kotlin/Worker.kt)): `./gradlew workflow-payment-kotlin:run`

### Starting Instances

To start ([code](./contracts/src/test/java/com/acme/workflows/booking/Start.java))
a `BookingWorkflow` instance, run:  `./gradlew contracts:booking-start --args '314'`.
Here '314' is an arbitrary string identifying the instance.

Acting as a host, you can send ([code](./contracts/src/test/java/com/acme/workflows/booking/Send.java))
a response to the booking request '314':

- Accept it: `./gradlew contracts:booking-send --args 'ACCEPTED 314'`
- Deny it: `./gradlew contracts:booking-send --args 'DENIED 314'`

To cancel ([code](./contracts/src/test/java/com/acme/workflows/booking/Cancel.java))
the instance: `./gradlew contracts:booking-cancel --args '314'`.

### Output

If you sent an `ACCEPTED` signal, the consoles should look like:

- From the `NotificationService` workers:

  ```
  05:23:00.633 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:314: Notifying Host Mary of a new request from John (attempt 1)
  05:23:10.927 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:314: Notifying Host Mary of a new request from John (attempt 2)
  05:23:18.819 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:314: Notifying Host Mary of a new request from John (attempt 3)
  05:23:19.364 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:314: Notifying Host Mary of a successful payment
  05:23:19.366 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:314: Notifying Traveler John of the success of her booking
  ```

- From the `PaymentWorkflow` workers:

  ```
  05:23:19.187 - 0190b6fd-b54a-7f89-8731-25a41a4f7c2c - PaymentWorkflow - Request customId:314: Running getDeposit
  ```

- From the `BookingWorkflow` workers:

  ```
  05:23:18.790 - 0190b6fd-6940-7784-8830-3035eba0eccc (customId:314) - BookingWorkflow - Booking accepted by Host Mary
  ```

