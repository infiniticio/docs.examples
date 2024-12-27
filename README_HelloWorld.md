# Infinitic Examples

## Hello World Workflow

The workflow and its implementation are described [here](https://docs.infinitic.io/docs/introduction/hello-world).

The `HelloWorkflow` ([Java](./workflow-hello/src/main/java/com/acme/workflows/hello/java/HelloWorkflowImpl.java),
[Kotlin](./workflow-hello/src/main/kotlin/com/acme/workflows/hello/kotlin/HelloWorkflowImpl.kt))
illustrates how to implement a simple workflow with two sequential tasks from a `HelloService`
([Java](./service-hello/src/main/java/com/acme/services/hello/java/HelloServiceImpl.java),
[Kotlin](./service-hello/src/main/kotlin/com/acme/services/hello/kotlin/HelloServiceImpl.kt))

It takes a `name` string as input and return `"Hello $name!"` using the following tasks:

- `sayHello` task: Takes a `name` string as input and returns `"Hello $name"`
- `addEnthusiasm` task: Takes a `str` string as input and returns `"$str!"`

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

To start ([code](./common/src/test/java/com/acme/clients/hello/Start.java))
1000 `HelloWorkflow` instances, run: `./gradlew common:hello-start --args 1000`.

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

To cancel ([code](./common/src/test/java/com/acme/clients/hello/CancelAll.java))
all `HelloWorkflow` instances, run: `./gradlew common:hello-cancel-all`.

