# Infinitic Examples

## Loyalty Workflow

The workflow and its implementation are described in the [docs](https://docs.infinitic.io/docs/introduction/examples#loyalty-program).

The `LoyaltyWorkflow`([Java](./workflow-loyalty/src/main/java/com/acme/workflows/loyalty/java/LoyaltyWorkflowImpl.java),
[Kotlin](./workflow-loyalty/src/main/kotlin/com/acme/workflows/loyalty/kotlin/LoyaltyWorkflowImpl.kt))
illustrates how to use the properties of a workflow to store a state.

A `LoyaltyWorlkflow` instance has:

- a public `points` properties
- a `start` method. Once started, `points` will be incremented every 10 seconds
- an `addBonus` method with a BonusEvent object as parameter. When a bonus event occurs,
`points` will be incremented by a value depending on the type of event.

### Deployment
To be able to run `LoyaltyWorlkflow` instances:

- Run a few `LoyaltyWorlkflow` workers

  - in Java ([config](./workflow-loyalty/src/main/resources/java/worker.yml)):
    `./gradlew workflow-loyalty:run-java`
  - and/or in Kotlin ([config](./workflow-loyalty/src/main/resources/kotlin/worker.yml)):
    `./gradlew workflow-loyalty:run-kotlin`

### Starting Instances

To start ([code](./common/src/test/java/com/acme/clients/loyalty/Start.java))
a `LoyaltyWorlkflow` instance, run: `./gradlew common:loyalty-start --args 'user42'`.
Here "user42" is an arbitrary string that identifies the instance.

We can call the `addBonus` method on this running instance to illustrate how to add bonus points
when a user does specific actions. To send ([code](./common/src/test/java/com/acme/clients/loyalty/Send.java))
a `BonusEvent` to this instance:

- for a `ORDER_COMPLETED`, run: `./gradlew common:loyalty-send --args 'ORDER_COMPLETED user42'`.
- for a `FORM_COMPLETED`, run: `./gradlew common:loyalty-send --args 'FORM_COMPLETED user42'`.

We can call the `burn` method on this running instance to illustrate how to remove bonus points
when a user does specific actions. To burn ([code](./common/src/test/java/com/acme/clients/loyalty/Burn.java))
1000 points: `./gradlew common:loyalty-burn --args '1000 user42'`.

To get the current value of `points`: `./gradlew common:loyalty-get --args 'user42'`.

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

To cancel ([code](./common/src/test/java/com/acme/clients/loyalty/Cancel.java)) the instance: `./gradlew common:loyalty-cancel --args 'user42'`.
