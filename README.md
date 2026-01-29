# Customer Transaction Analyzer

Mini-Projekt zur Analyse von Kundentransaktionen in Java. Demonstriert Collections, Streams, Custom Exceptions und TDD.

## Quickstart

1. Importiere das Projekt in IntelliJ als Maven-Projekt.
2. `mvn test` führt Unit-Tests aus.
3. `mvn verify` führt Integrationstests (Failsafe) aus, falls konfiguriert.
4. `java -cp target/customer-transaction-analyzer-1.0.0-SNAPSHOT.jar com.example.transactionanalyzer.cli.Main` startet die Demo.

## Struktur

- `core.model` Transaction
- `core.exception` Custom Exceptions
- `repository` InMemoryRepository
- `io` CSV Reader
- `service` Analyzer mit Streams
- `report` Report Generator
- `cli` Demo Main

## TDD

Tests liegen unter `src/test/java`. Integrationstest unter `src-integration/java`.