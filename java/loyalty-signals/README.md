# Loyalty App For Infinitic

The workflow `LoyaltyWorkflowImpl` describes a workflow per user, where 3 services are applied sequentially for every signal received 

## Before running
- If needed, update credentials in `src/main/resources/infinitic.yml`

- You don't need to update `infinitic.yml` if you use a local standalone Pulsar instance. Run this Pulsar instance using `docker compose up`.

## Running services all together
run `./gradlew run`

## Launch
Launch one workflow! `./gradlew dispatch`

## Dashboard
run `./gradlew dashboard` (on port 9080)
