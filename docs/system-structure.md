# System Structure

## System Behavior (Architecture)

## Architecture Style

- Monolith

## Architecture Diagram

Frontend and Monolithic Backend  mimicing the real life project.

```mermaid
---
config:
  layout: fixed
---
flowchart LR
 subgraph Monolith["<b>Flight Reservation System (FRS)</b>"]
        FE["Frontend"]
        BE["Backend"]
        DB[("Database")]
  end
 subgraph subGraph1["External Systems"]
        GDS["<b>GDS</b>"]
        CRS["<b>CRS</b>"]
  end
    FE --> BE
    BE --> DB & GDS & CRS
```

## Tech Stack

Programming Languages:

- Java
- Angular

Database:

- MariaDB

## Repository Strategy

Mono-repo Repository Structure

[Flight Reservation System Repository](https://github.com/ArkCase/atdd-flight-reserve)
