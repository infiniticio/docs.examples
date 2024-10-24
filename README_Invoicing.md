# Infinitic Examples

## Invoicing

The workflow and its implementation are described [here](https://docs.infinitic.io/docs/introduction/examples#monthly-invoicing).

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

To start ([code](./common/src/test/java/com/acme/clients/booking/Start.java))
3 `InvoicingWorkflow` instances, run:  `./gradlew common:invoicing-start --args '3'`.

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

To cancel ([code](./common/src/test/java/com/acme/clients/invoicing/Cancel.java))
all instances: `./gradlew common:invoicing-cancel-all`.

