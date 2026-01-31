# Test Strategy

## Purpose
Diese Teststrategie beschreibt Ziele, Testarten, Ablageorte, CI‑Integration und Verantwortlichkeiten für das Projekt.

## Scope
Gilt für alle Module unter `com.example.transactionanalyzer`. Deckt Unit, Integration und End‑to‑End Tests ab.

## Testarten and Locations
- **Unit Tests**
    - Ort: `src/test/java`
    - Ziel: schnelle, deterministische Tests ohne I/O oder externe Abhängigkeiten
    - Ausführen: `mvn test`

- **Integration Tests**
    - Ort: `src/integration-test/java` oder `src/test/java` mit `@Tag("integration")`
    - Ziel: Zusammenspiel von Komponenten, echte I/O (Dateisystem)
    - Ausführen: `mvn verify` (Failsafe) oder CI Job

- **End‑to‑End Tests**
    - Ort: `src/integration-test/java`
    - Ziel: komplette Pipeline (Reader → Repo → Analyzer → Report)

## TDD Workflow
1. Test schreiben (rot) — kleinste Änderung
2. Minimalen Code implementieren (grün)
3. Refactor und Cleanup
4. Commit mit aussagekräftiger Nachricht

## Testdaten
- Ablage: `src/test/resources/sample-data/`
- Keine sensiblen Daten im Repo. Secrets via CI Environment Variables.

## Testdoubles
- Bevorzugt: InMemory Fakes für Repositories
- Mocks nur für externe Integrationen oder Interaktionsprüfungen

## CI Integration
- Unit Tests: `mvn -B -DskipTests=false test`
- Integration Tests: `mvn -B -DskipTests=false verify`
- Testreports: Surefire/Failsafe JUnit XML in `target/surefire-reports` und `target/failsafe-reports`

## Quality Gates
- Mindestcoverage: 70% (anpassbar)
- Kritische Module: 90%+
- Flaky Tests markieren mit `@Tag("flaky")` und untersuchen

## Failure Handling
- Fehlschlag in PR: Tests fixen vor Merge
- CI‑Fehler: Issue erstellen, Verantwortlichen benachrichtigen

## Naming Conventions
- Testklasse: `<ClassName>Test`
- Integration: `<ClassName>IT` oder `*IntegrationTest`
- Testmethoden: `shouldDoXWhenY` oder `givenX_whenY_thenZ`

## Responsibilities
- Developer: schreibt Unit Tests für neuen Code
- Reviewer: prüft Tests in Code Review
- Maintainer: CI, Testdaten, Metriken

## Maintenance
- Coverage Review alle 2 Wochen
- Refactor Sessions nach größeren Feature‑Sprints