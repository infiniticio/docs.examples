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

To use a different Pulsar cluster or database, update the [`infinitic.yml`](./common/src/main/resources/infinitic.yml) file.
(Refer to the [Infinitic documentation](https://docs.infinitic.io/docs/workflows/workers#configuration-file) for configuration details.)

### Repository Structure

This repository is orchestrated in alignment with Infinitic's aim to streamline
the development of distributed applications. Here's how it is structured:

- Individual modules are allocated for each service and workflow. This modular structure promotes independent maintenance of each component.
- The `contracts` module houses the Java interfaces and shared objects that form the contract for all services and workflows. 
This encapsulates the agreements between various parts of your system in a centralized place.
- A `common` module contains helper classes used in Services and Workflows implementation.

### Worker Implementation

For simplicity and consistency, all services and workflows in these examples use
a common [Worker implementation](./common/src/main/java/com/acme/common/Worker.java).

The worker's behavior is customized through a configuration file, received as the first argument 
when launched through the gradle tasks defined in the global [build file](./build.gradle).

Configuration files are stored in the `resources` folder of each Workflow and Service,
and are available in two versions, respectively targeting the Java or the Kotlin implementation.

### Workflows Dispatching

For demonstration purposes, Workflows are dispatched through [gradle tasks](./common/build.gradle) 
defined in the gradle build file of the `common` module.
The code of those tasks is on the [test](./common/src/test/java/com/acme/clients) folder of `common`. 

