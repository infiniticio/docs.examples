# Hello World App For Infinitic

The workflow `HelloWorld` takes a `name` string as input and return `"Hello $name!"` using sequentially 2 tasks run on distributed workers:

- a `sayHello` task that takes a `name` string as input and returns `"Hello $name"`
- an `addEnthusiasm` task that takes a `str` string as input and returns `"$str!"`

This repository is described in the [Infinitic documentation](https://docs.infinitic.io/docs/introduction/hello-world).

## Before running
If needed, update credentials in `infinitic.yml`.

You don't need to update `infinitic.yml` if you use a local standalone Pulsar instance. Run this Pulsar instance using `docker compose up`.

## Running services all together
run `./gradlew run`

## Launch
Launch 10 workflows! `./gradlew dispatch` (see Client.java main function)

## Dashboard
run `./gradlew dashboard` - server will run on port indicated in `infinitic.yml`
