# Infinitic Examples

This repository contains implementations of the examples described in the Infinitic documentation:

- https://docs.infinitic.io/docs/introduction/hello-world
- https://docs.infinitic.io/docs/introduction/examples

## Project Overview and Setup Guide

### Languages

While this repository showcases both Java and Kotlin implementations side-by-side, 
in a real-world scenario, you would typically choose one language for your project.
The dual implementation here is purely for demonstration purposes.

We encourage you to explore the implementation in your preferred language
and refer to the corresponding documentation for detailed explanations.

### Prerequisites

To run the examples, you'll need:

1. A [Pulsar](https://pulsar.apache.org) cluster
2. A database (MySQL, PostgreSQL, or Redis)

For quick setup, we recommend using Docker:

1. Install [Docker](https://docs.docker.com/engine/install/) on your system.
2. Clone this repository to your local machine.
3. Navigate to the project root folder.
4. Run `docker compose up` to start local Pulsar and Redis instances.

To use a different Pulsar cluster or database, update the [`infinitic.yml`](./contracts/src/main/resources/infinitic.yml) file.
(Refer to the [Infinitic documentation](https://docs.infinitic.io/docs/workflows/workers) for configuration details.)

### Repository Structure

This repository is structured to reflect Infinitic's mission of simplifying distributed application implementation:

- Each service and workflow has its own module and can be implemented independently.
- All service and workflows interfaces and shared objects are located in the `contracts` module.

The `contracts` module:
- serves as the central point for all interactions between Workflows and Services, and Workflows and other Workflows
  (There are no direct interactions between services when using Infinitic).
- is the only module needed for an Infinitic client to dispatch a task or a workflow.

### Worker Implementation

For simplicity and consistency, all services and workflows in this repository use
a common [Worker implementation](./contracts/src/main/java/com/acme/utils/Worker.java).

The worker's behavior is customized through a configuration file, received as the first argument 
when launched through the gradle tasks defined in the global [build file](./build.gradle).

Configuration files are stored in the `resources` folder of each Workflow and Service,
and are available in two versions:
  1. Java implementation
  2. Kotlin implementation

### Workflows Dispatching

For demonstration purposes, Workflows are dispatched through [gradle tasks](./contracts/build.gradle) defined in the 
`contracts` repository. The code used to launch them is [here](./contracts/src/test/java/com/acme/workflows).

## Hello World 

The `HelloWorkflow` ([Java](./workflow-hello/src/main/java/com/acme/workflows/hello/java/HelloWorkflowImpl.java),
[Kotlin](./workflow-hello/src/main/kotlin/com/acme/workflows/hello/kotlin/HelloWorkflowImpl.kt))
illustrates how to implement a simple workflow with two sequential tasks from a `HelloService`
([Java](./service-hello/src/main/java/com/acme/services/hello/java/HelloServiceImpl.java),
[Kotlin](./service-hello/src/main/kotlin/com/acme/services/hello/kotlin/HelloServiceImpl.kt))

It takes a `name` string as input and return `"Hello $name!"` using the following tasks:

- `sayHello` task: Takes a `name` string as input and returns `"Hello $name"`
- `addEnthusiasm` task: Takes a `str` string as input and returns `"$str!"`

The workflow is described in https://docs.infinitic.io/docs/introduction/hello-world.

### Deployment
To be able to run `HelloWorkflow` instances:

- Run a few `HelloService` workers

  - in Java ([config](./service-hello/src/main/resources/java/worker.yml)):
    `./gradlew service-hello:run-java`

  - and/or in Kotlin ([config](./service-hello/src/main/resources/kotlin/worker.yml)):
    `./gradlew service-hello:run-kotlin`

- Run a few `HelloWorkflow` workers

  - in Java ([config](./workflow-hello/src/main/resources/java/worker.yml)):
    `./gradlew workflow-hello:run-java`
  - and/or in Kotlin ([config](./workflow-hello/src/main/resources/kotlin/worker.yml)):
    `./gradlew workflow-hello:run-kotlin`

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
all `HelloWorkflow` instances, run: `./gradlew contracts:hello-cancel-all`.

## Saga Workflow

The `SagaWorkflow` ([Java](./workflow-saga/src/main/java/com/acme/workflows/saga/java/SagaWorkflowImpl.java),
[Kotlin](./workflow-saga/src/main/kotlin/com/acme/workflows/saga/kotlin/SagaWorkflowImpl.kt))
implements a booking process combining a car rental, a flight, and a hotel reservation.
We require that all 3 bookings have to be successful together:
if any of them fails, we should cancel the other bookings that were successful.

This workflow combines 3 services:
- `CarRentalService` ([Java](./service-car-rental/src/main/java/com/acme/services/carRental/java/CarRentalServiceImpl.java),
  [Kotlin](./service-car-rental/src/main/kotlin/com/acme/services/carRental/kotlin/CarRentalServiceImpl.kt))
- `FlightBookingService` ([Java](./service-flight-booking/src/main/java/com/acme/services/flightBooking/java/FlightBookingServiceImpl.java),
  [Kotlin](./service-flight-booking/src/main/kotlin/com/acme/services/flightBooking/kotlin/FlightBookingServiceImpl.kt))
- `HotelBookingService` ([Java](./service-hotel-booking/src/main/java/com/acme/services/hotelBooking/java/HotelBookingServiceImpl.java),
  [Kotlin](./service-hotel-booking/src/main/kotlin/com/acme/services/hotelBooking/kotlin/HotelBookingServiceImpl.kt))

Each of them have `book`, and `cancel` methods.
This last one is used to cancel a previously booked command.

The workflow implementation is described in https://docs.infinitic.io/docs/introduction/examples#bookings-and-saga

### Deployment
To be able to run `SagaWorkflow` instances:

- Run a few `CarRentalService` workers

  - in Java ([config](./service-car-rental/src/main/resources/java/worker.yml)):
    `./gradlew service-car-rental:run-java`

  - and/or in Kotlin ([config](./service-car-rental/src/main/resources/kotlin/worker.yml)):
    `./gradlew service-car-rental:run-kotlin`

- Run a few `FlightBookingService` workers

  - in Java ([config](./service-flight-booking/src/main/resources/java/worker.yml)):
    `./gradlew service-flight-booking:run-java`

  - and/or in Kotlin ([config](./service-flight-booking/src/main/resources/kotlin/worker.yml)):
    `./gradlew service-flight-booking:run-kotlin`

- Run a few `HotelBookingService` workers

  - in Java ([config](./service-hotel-booking/src/main/resources/java/worker.yml)):
    `./gradlew service-hotel-booking:run-java`

  - and/or in Kotlin ([config](./service-hotel-booking/src/main/resources/kotlin/worker.yml)):
    `./gradlew service-hotel-booking:run-kotlin`

- Run a few `SagaWorkflow` workers

  - in Java ([config](./workflow-saga/src/main/resources/java/worker.yml)):
    `./gradlew workflow-saga:run-java`
  - and/or in Kotlin ([config](./workflow-saga/src/main/resources/kotlin/worker.yml)):
    `./gradlew workflow-saga:run-kotlin`

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
all `SagaWorkflow` instances, run: `./gradlew contracts:saga-cancel-all`.

## Invoicing

The `InvoicingWorkflow`([Java](./workflow-invoicing/src/main/java/com/acme/workflows/invoicing/java/InvoicingWorkflowImpl.java),
[Kotlin](./workflow-invoicing/src/main/kotlin/com/acme/workflows/invoicing/kotlin/InvoicingWorkflowImpl.kt))
implements a simple process to prepare and send an invoice to a user every month. It includes:

- the `MetricsService` ([Java](./service-metrics/src/main/java/com/acme/services/metrics/java/MetricsServiceImpl.java),
  [Kotlin](./service-metrics/src/main/kotlin/com/acme/services/metrics/kotlin/MetricsServiceImpl.kt))
- the `InvoiceService` ([Java](./service-invoice/src/main/java/com/acme/services/invoice/java/InvoiceServiceImpl.java),
  [Kotlin](./service-invoice/src/main/kotlin/com/acme/services/invoice/kotlin/InvoiceServiceImpl.kt))
- the `NotificationService` ([Java](./service-notification/src/main/java/com/acme/services/notification/java/NotificationServiceImpl.java),
  [Kotlin](./service-notification/src/main/kotlin/com/acme/services/notification/kotlin/NotificationServiceImpl.kt))
- the `PaymentWorkflow` ([Java](./workflow-payment/src/main/java/com/acme/workflows/payment/java/PaymentWorkflowImpl.java),
  [Kotlin](./workflow-payment/src/main/kotlin/com/acme/workflows/payment/kotlin/PaymentWorkflowImpl.kt))

The workflow is described in https://docs.infinitic.io/docs/introduction/examples#monthly-invoicing.

### Deployment
To be able to run `InvoicingWorkflow` instances:

- Run a few `MetricsService` workers

  - in Java ([config](./service-metrics/src/main/resources/java/worker.yml)): `./gradlew service-metrics:run-java`
  - and/or in Kotlin ([config](./service-metrics/src/main/resources/kotlin/worker.yml)): `./gradlew service-metrics:run-kotlin`

- Run a few `InvoiceService` workers

  - in Java ([config](./service-invoice/src/main/resources/java/worker.yml)): `./gradlew service-invoice:run-java`
  - and/or in Kotlin ([config](./service-invoice/src/main/resources/kotlin/worker.yml)): `./gradlew service-invoice:run-kotlin`
  
- Run a few `NotificationService` workers

  - in Java ([config](./service-notification/src/main/resources/java/worker.yml)): `./gradlew service-notification:run-java`
  - and/or in Kotlin ([config](./service-notification/src/main/resources/kotlin/worker.yml)): `./gradlew service-notification:run-kotlin`

- Run a few `InvoicingWorkflow` workers

  - in Java ([config](./workflow-invoicing/src/main/resources/java/worker.yml)): `./gradlew workflow-invoicing:run-java`
  - and/or in Kotlin ([config](./workflow-invoicing/src/main/resources/kotlin/worker.yml)): `./gradlew workflow-invoicing:run-kotlin`

- Run a few `PaymentWorkflow` workers

  - in Java ([config](./workflow-payment/src/main/resources/java/worker.yml)): `./gradlew workflow-payment:run-java`
  - and/or in Kotlin ([config](./workflow-payment/src/main/resources/kotlin/worker.yml)): `./gradlew workflow-payment:run-kotlin`

### Starting Instances

To start ([code](./contracts/src/test/java/com/acme/workflows/booking/Start.java))
3 `InvoicingWorkflow` instances, run:  `./gradlew contracts:invoicing-start --args '3'`.

The consoles should look like (Notes: for convenience, in the examples the invoices are generated every minute instead of every day):

- From the `MetricsService` workers:

  ```
  11:51:30.267 - 0190ccf9-a084-70ad-8818-7551d1699690 - MetricsService - Amount calculated for user(user1), from 2024-07-19T23:50:30.050141 to 2024-07-19T23:51:30.050141: 29853.67890799339
  11:51:30.271 - 0190ccf9-a089-772f-8e59-f93cec7ffba0 - MetricsService - Amount calculated for user(user2), from 2024-07-19T23:50:30.053090 to 2024-07-19T23:51:30.053090: 59030.74157522818
  11:51:30.271 - 0190ccf9-9d50-7bcf-82b1-0c0faa92308a - MetricsService - Amount calculated for user(user0), from 2024-07-19T23:50:30.050141 to 2024-07-19T23:51:30.050141: 58197.807882608824
  11:51:30.528 - 0190ccf9-a084-70ad-8818-7551d1699690 - MetricsService - Checking if user (user1) is still subscribed... true
  11:51:30.535 - 0190ccf9-9d50-7bcf-82b1-0c0faa92308a - MetricsService - Checking if user (user0) is still subscribed... true
  11:51:30.537 - 0190ccf9-a089-772f-8e59-f93cec7ffba0 - MetricsService - Checking if user (user2) is still subscribed... true
  11:52:31.279 - 0190ccf9-a089-772f-8e59-f93cec7ffba0 - MetricsService - Amount calculated for user(user2), from 2024-07-19T23:51:30.603804 to 2024-07-19T23:52:30.603804: 31958.914047550847
  11:52:31.288 - 0190ccf9-a084-70ad-8818-7551d1699690 - MetricsService - Amount calculated for user(user1), from 2024-07-19T23:51:30.596672 to 2024-07-19T23:52:30.596672: 23749.17903280677
  11:52:31.288 - 0190ccf9-9d50-7bcf-82b1-0c0faa92308a - MetricsService - Amount calculated for user(user0), from 2024-07-19T23:51:30.603851 to 2024-07-19T23:52:30.603851: 19653.730178595615
  11:52:31.480 - 0190ccf9-a089-772f-8e59-f93cec7ffba0 - MetricsService - Checking if user (user2) is still subscribed... true
  11:52:31.497 - 0190ccf9-a084-70ad-8818-7551d1699690 - MetricsService - Checking if user (user1) is still subscribed... true
  11:52:31.510 - 0190ccf9-9d50-7bcf-82b1-0c0faa92308a - MetricsService - Checking if user (user0) is still subscribed... true
  ```

- From the `InvoiceService` workers:

  ```
  11:51:30.424 - 0190ccf9-a084-70ad-8818-7551d1699690 - InvoiceService - Creating new Invoice for user (user1), amount (29853.67890799339), start (2024-07-19T23:50:30.050141), end (2024-07-19T23:51:30.050141)
  11:51:30.424 - 0190ccf9-a089-772f-8e59-f93cec7ffba0 - InvoiceService - Creating new Invoice for user (user2), amount (59030.74157522818), start (2024-07-19T23:50:30.053090), end (2024-07-19T23:51:30.053090)
  11:51:30.424 - 0190ccf9-9d50-7bcf-82b1-0c0faa92308a - InvoiceService - Creating new Invoice for user (user0), amount (58197.807882608824), start (2024-07-19T23:50:30.050141), end (2024-07-19T23:51:30.050141)
  11:52:31.393 - 0190ccf9-a089-772f-8e59-f93cec7ffba0 - InvoiceService - Creating new Invoice for user (user2), amount (31958.914047550847), start (2024-07-19T23:51:30.603804), end (2024-07-19T23:52:30.603804)
  11:52:31.406 - 0190ccf9-a084-70ad-8818-7551d1699690 - InvoiceService - Creating new Invoice for user (user1), amount (23749.17903280677), start (2024-07-19T23:51:30.596672), end (2024-07-19T23:52:30.596672)
  11:52:31.431 - 0190ccf9-9d50-7bcf-82b1-0c0faa92308a - InvoiceService - Creating new Invoice for user (user0), amount (19653.730178595615), start (2024-07-19T23:51:30.603851), end (2024-07-19T23:52:30.603851)
  ```

- From the `NotificationService` workers:

  ```
  11:51:30.481 - 0190ccf9-9d50-7bcf-82b1-0c0faa92308a - NotificationService - Send Invoice 03dfe0a5-84ee-4b74-8912-f228dac21488 to user user0
  11:51:30.481 - 0190ccf9-a089-772f-8e59-f93cec7ffba0 - NotificationService - Send Invoice 255ab497-bdd4-4cd1-a9cf-e37290412309 to user user2
  11:51:30.486 - 0190ccf9-a084-70ad-8818-7551d1699690 - NotificationService - Send Invoice 0162c469-e83e-49b8-a111-40557aa505cd to user user1
  11:52:31.437 - 0190ccf9-a089-772f-8e59-f93cec7ffba0 - NotificationService - Send Invoice be907448-81af-440d-9bbb-cbb71ca1e48c to user user2
  11:52:31.446 - 0190ccf9-a084-70ad-8818-7551d1699690 - NotificationService - Send Invoice ec8ef92c-47a0-4efc-8581-243dda9d4c6b to user user1
  11:52:31.470 - 0190ccf9-9d50-7bcf-82b1-0c0faa92308a - NotificationService - Send Invoice 871bab82-a72a-412e-bfcf-98ad2d988115 to user user0
  ```

- From the `PaymentWorkflow` workers:

  ```
  11:51:30.348 - 0190ccfa-8bdf-7569-9cc7-bfdb7ab796d3 - PaymentWorkflow - Get payment 58197.807882608824 from User user0
  11:51:30.348 - 0190ccfa-8bda-7e4e-aa73-b052ac1322b9 - PaymentWorkflow - Get payment 29853.67890799339 from User user1
  11:51:30.348 - 0190ccfa-8bdf-763d-bc7c-f46004d63ba8 - PaymentWorkflow - Get payment 59030.74157522818 from User user2
  11:52:31.325 - 0190ccfb-7a2f-7776-b459-d05b9614b78b - PaymentWorkflow - Get payment 31958.914047550847 from User user2
  11:52:31.358 - 0190ccfb-7a38-7c80-b97e-b5ede2cc0835 - PaymentWorkflow - Get payment 23749.17903280677 from User user1
  11:52:31.358 - 0190ccfb-7a38-76e1-acc3-3c7141167191 - PaymentWorkflow - Get payment 19653.730178595615 from User user0
  ```

### Canceling Instances

To cancel ([code](./contracts/src/test/java/com/acme/workflows/invoicing/Cancel.java))
all instances: `./gradlew contracts:invoicing-cancel-all`.

## Loyalty

The `LoyaltyWorkflow`([Java](./workflow-loyalty/src/main/java/com/acme/workflows/loyalty/java/LoyaltyWorkflowImpl.java),
[Kotlin](./workflow-loyalty/src/main/kotlin/com/acme/workflows/loyalty/kotlin/LoyaltyWorkflowImpl.kt))
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

  - in Java ([config](./workflow-loyalty/src/main/resources/java/worker.yml)):
    `./gradlew workflow-loyalty:run-java`
  - and/or in Kotlin ([config](./workflow-loyalty/src/main/resources/kotlin/worker.yml)):
    `./gradlew workflow-loyalty:run-kotlin`

### Starting Instances

To start ([code](./contracts/src/test/java/com/acme/workflows/loyalty/Start.java))
a `LoyaltyWorlkflow` instance, run: `./gradlew contracts:loyalty-start --args 'user42'`.
Here "user42" is an arbitrary string that identifies the instance.

We can call the `addBonus` method on this running instance to illustrate how to add bonus points
when a user does specific actions. To send ([code](./contracts/src/test/java/com/acme/workflows/loyalty/Send.java))
a `BonusEvent` to this instance:

- for a `ORDER_COMPLETED`, run: `./gradlew contracts:loyalty-send --args 'ORDER_COMPLETED user42'`.
- for a `FORM_COMPLETED`, run: `./gradlew contracts:loyalty-send --args 'FORM_COMPLETED user42'`.

We can call the `burn` method on this running instance to illustrate how to remove bonus points
when a user does specific actions. To burn ([code](./contracts/src/test/java/com/acme/workflows/loyalty/Burn.java))
1000 points: `./gradlew contracts:loyalty-burn --args '1000 user42'`.

To get the current value of `points`: `./gradlew contracts:loyalty-get --args 'user42'`.

The console should look like:

```
03:55:58.115 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 0
03:56:08.492 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 1
03:56:18.068 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - received ORDER_COMPLETED - new points = 501
03:56:18.497 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 502
03:56:28.507 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 503
03:56:38.499 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 504
03:56:41.686 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - received FORM_COMPLETED - new points = 704
03:56:48.499 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 705
03:56:58.487 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 706
03:56:58.579 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - unable to burn 1000 - insufficient points = 706
03:57:08.508 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 707
03:57:12.219 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - received ORDER_COMPLETED - new points = 1207
03:57:18.494 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 1208
03:57:21.924 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - burnt 1000 - new points = 208
03:57:28.500 - 0190c620-cf4c-7a8b-86d4-d206775d85c7 (customId:user42) - LoyaltyWorkflow - points = 209
```

### Canceling

To cancel ([code](./contracts/src/test/java/com/acme/workflows/loyalty/Cancel.java)) the instance: `./gradlew  contracts:loyalty-cancel --args 'user42'`.

## Booking

The `BookingWorkflow`([Java](./workflow-booking/src/main/java/com/acme/workflows/booking/java/BookingWorkflowImpl.java), [Kotlin](./workflow-booking/src/main/kotlin/com/acme/workflows/booking/kotlin/BookingWorkflowImpl.kt))
implements a communication process during a location booking, that includes:

- the `NotificationService` ([Java](./service-notification/src/main/java/com/acme/services/notification/java/NotificationServiceImpl.java),[Kotlin](./service-notification/src/main/kotlin/com/acme/services/notification/kotlin/NotificationServiceImpl.kt))
- the `PaymentWorkflow` ([Java](./workflow-payment/src/main/java/com/acme/workflows/payment/java/PaymentWorkflowImpl.java),[Kotlin](./workflow-payment/src/main/kotlin/com/acme/workflows/payment/kotlin/PaymentWorkflowImpl.kt))

The workflow is described in https://docs.infinitic.io/docs/introduction/examples#location-booking.

### Deployment
To be able to run `BookingWorkflow` instances:

- Run a few `NotificationService` workers 

  - in Java ([config](./service-notification/src/main/resources/java/worker.yml)):`./gradlew service-notification:run-java`
  - and/or in Kotlin ([config](./service-notification/src/main/resources/kotlin/worker.yml)): `./gradlew service-notification:run-kotlin`

- Run a few `BookingWorkflow` workers

  - in Java ([config](./workflow-booking/src/main/resources/java/worker.yml)): `./gradlew workflow-booking:run-java`
  - and/or in Kotlin ([config](./workflow-booking/src/main/resources/kotlin/worker.yml)): `./gradlew workflow-booking:run-kotlin`

- Run a few `PaymentWorkflow` workers

  - in Java ([config](./workflow-payment/src/main/resources/java/worker.yml)): `./gradlew workflow-payment:run-java`
  - and/or in Kotlin ([config](./workflow-payment/src/main/resources/kotlin/worker.yml)): `./gradlew workflow-payment:run-kotlin`

### Starting Instances

To start ([code](./contracts/src/test/java/com/acme/workflows/booking/Start.java)) a `BookingWorkflow` instance, run:  `./gradlew contracts:booking-start --args 'booking314'`.
Here 'booking314' is an arbitrary string identifying the instance.

Acting as a host, you can send ([code](./contracts/src/test/java/com/acme/workflows/booking/Send.java))
a response to the booking request 'booking314':

- Accept it: `./gradlew contracts:booking-send --args 'ACCEPTED booking314'`
- Deny it: `./gradlew contracts:booking-send --args 'DENIED booking314'`

If you sent an `ACCEPTED` signal, the consoles should look like:

- From the `NotificationService` workers:

  ```
  05:23:00.633 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:booking314: Notifying Host Mary of a new request from John (attempt 1)
  05:23:10.927 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:booking314: Notifying Host Mary of a new request from John (attempt 2)
  05:23:18.819 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:booking314: Notifying Host Mary of a new request from John (attempt 3)
  05:23:19.364 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:booking314: Notifying Host Mary of a successful payment
  05:23:19.366 - 0190b6fd-6940-7784-8830-3035eba0eccc - NotificationService - Request customId:booking314: Notifying Traveler John of the success of her booking
  ```

- From the `PaymentWorkflow` workers:

  ```
  05:23:19.187 - 0190b6fd-b54a-7f89-8731-25a41a4f7c2c - PaymentWorkflow - Request customId:booking314: Running getDeposit
  ```

- From the `BookingWorkflow` workers:

  ```
  05:23:18.790 - 0190b6fd-6940-7784-8830-3035eba0eccc (customId:booking314) - BookingWorkflow - Booking accepted by Host Mary
  ```

### Canceling instances

To cancel ([code](./contracts/src/test/java/com/acme/workflows/booking/Cancel.java)):

- the `booking314` instance: `./gradlew contracts:booking-cancel --args 'booking314'`
- all instances: `./gradlew contracts:booking-cancel-all`.