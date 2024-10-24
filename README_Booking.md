# Booking Workflow

The workflow and its implementation are described in the [docs](https://docs.infinitic.io/docs/introduction/examples#location-booking).

The `BookingWorkflow`([Java](./workflow-booking/src/main/java/com/acme/workflows/booking/java/BookingWorkflowImpl.java), [Kotlin](./workflow-booking/src/main/kotlin/com/acme/workflows/booking/kotlin/BookingWorkflowImpl.kt))
implements a communication process during a location booking, that includes:

- the `NotificationService` ([Java](./service-notification/src/main/java/com/acme/services/notification/java/NotificationServiceImpl.java), [Kotlin](./service-notification/src/main/kotlin/com/acme/services/notification/kotlin/NotificationServiceImpl.kt))
- the `PaymentWorkflow` ([Java](./workflow-payment/src/main/java/com/acme/workflows/payment/java/PaymentWorkflowImpl.java), [Kotlin](./workflow-payment/src/main/kotlin/com/acme/workflows/payment/kotlin/PaymentWorkflowImpl.kt))

## Deployment

To be able to run `BookingWorkflow` instances:

### Java implementation:

- Run at least one `NotificationService` worker: ([config](./service-notification/src/main/resources/java/worker.yml)): `./gradlew service-notification:run-java`
- Run at least one `BookingWorkflow` worker ([config](./workflow-booking/src/main/resources/java/worker.yml)): `./gradlew workflow-booking:run-java`
- Run at least one `PaymentWorkflow` worker ([config](./workflow-payment/src/main/resources/java/worker.yml)): `./gradlew workflow-payment:run-java`

### Kotlin implementation:

- Run at least one `NotificationService` worker: ([config](./service-notification/src/main/resources/kotlin/worker.yml)): `./gradlew service-notification:run-kotlin`
- Run at least one `BookingWorkflow` worker ([config](./workflow-booking/src/main/resources/kotlin/worker.yml)): `./gradlew workflow-booking:run-kotlin`
- Run at least one `PaymentWorkflow` worker ([config](./workflow-payment/src/main/resources/kotlin/worker.yml)): `./gradlew workflow-payment:run-kotlin`
  
## Starting Instances

To start ([code](./common/src/test/java/com/acme/clients/booking/Start.java)) a `BookingWorkflow` instance, run:  `./gradlew common:booking-start --args 'booking314'`.
Here 'booking314' is an arbitrary string identifying the instance.

Acting as a host, you can send ([code](./common/src/test/java/com/acme/clients/booking/Send.java))
a response to the booking request 'booking314':

- Accept it: `./gradlew common:booking-send --args 'ACCEPTED booking314'`
- Deny it: `./gradlew common:booking-send --args 'DENIED booking314'`

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

## Canceling instances

To cancel ([code](./common/src/test/java/com/acme/clients/booking/Cancel.java)):

- the `booking314` instance: `./gradlew common:booking-cancel --args 'booking314'`
- all instances: `./gradlew common:booking-cancel-all`.