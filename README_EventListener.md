# Event Listener


## Deployment
To run `SagaWorkflow` instances, you need to start the following services and workflow:

### Java implementation

- Run at least one `CarRentalService` ([code](./service-car-rental/src/main/java/com/acme/services/carRental/java/CarRentalServiceImpl.java)) worker ([config](./service-car-rental/src/main/resources/java/worker.yml)):
    `./gradlew event-listener:run-java`


### Kotlin implementation

- Run at least one `CarRentalService` ([code](./service-car-rental/src/main/kotlin/com/acme/services/carRental/kotlin/CarRentalServiceImpl.kt)) worker ([config](./service-car-rental/src/main/resources/kotlin/worker.yml)):
  `./gradlew event-listener:run-kotlin`

