# DataFlowEngine: Real-Time Event Processing PoC

A lightweight Java-based system for real-time event processing, showcasing producer-consumer patterns, sliding window analytics, dynamic rule evaluation, and live stats via a REST API.

## Features
- **Producer-Consumer Pattern**: Uses `BlockingQueue` for efficient data transfer.
- **Real-Time Analytics**: Sliding window analytics with metrics like average/min/max values, top sensors, and throughput.
- **Dynamic Rules**: Configurable rule engine loaded from JSON.
- **Live Stats API**: Embedded Jetty server exposing `/stats` endpoint for real-time metrics.
- **Metrics & Observability**: Periodic logging of throughput, rule pass rates, and more.
- **Concurrency**: Multi-threaded design with separate threads for generation, processing, and stats.

## Architecture
DataGenerator] --> [BlockingQueue] --> [DataProcessor] --> [SlidingWindowAnalyzer]
|                   |
v                   v
[RuleEngine]         [StatsServer (/stats)]

## Prerequisites
- Java 11 or higher
- Maven (for dependencies)
- `config.json` file in the project root:
  ```json
  {
    "thresholdValue": 50
  }
