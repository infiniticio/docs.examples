# Loyalty App For Infinitic

The workflow `LoyalyImpl` run a loyalty program where a user earns a point each week (emulated as a few seconds).
Some bonus points can be added based on events.

This repository is described in the Infinitic documentation: https://docs.infinitic.io/workflows/properties

## Before running
- If needed, update credentials in `src/main/resources/infinitic.yml`

- You don't need to update `infinitic.yml` if you use a local standalone Pulsar instance. Run this Pulsar instance using `docker compose up`.

## Running services all together
run `./gradlew run`

## Launch
Launch one workflow! `./gradlew dispatch`

## Dashboard
run `./gradlew dashboard` (on port 9080)
