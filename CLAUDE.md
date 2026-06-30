# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Keel Logger API (`io.github.sinri:keel-logger-api`) — a Java 17 logging **SDK** that defines a structured, context-aware logging model and an output contract (`LogWriterAdapter`). It is a pure API module: it ships **no** SLF4J/Log4j binding and **no** JSON formatting; the default writer (`BaseLogWriter`) prints plain text to stdout. Metric recording (`MetricRecorder`) is a separate contract with no built-in backend.

- **Java**: 17 (toolchain + `--release 17`)
- **Build**: Gradle (Kotlin DSL, `build.gradle.kts`), wrapper present
- **JPMS module**: `io.github.sinri.keel.logger.api` (see `src/main/java/module-info.java`)
- **License**: dual GPL-3.0-only OR LGPL-3.0-only (recipient picks one)
- **Annotations**: `org.jspecify` is `compileOnly` / `requires static` — types are `@NullMarked` by default; do not add jspecify as a runtime dependency.

## Commands

```bash
./gradlew build                       # compile + test + jars
./gradlew test                        # run tests
./gradlew test --tests "io.github.sinri.keel.logger.api.log.LogTest"          # single test class
./gradlew test --tests "*.LogLevelTest"                                       # single test by name pattern
./gradlew javadoc                     # output -> build/docs/javadoc/
./gradlew tasks --group=publishing    # list publishing tasks (names vary: ...Repository / ...Repositories)
```

Tests are JUnit 5 (`useJUnitPlatform()`). The `test` task is restricted via `include("io/github/sinri/keel/logger/api/**/*Test.class")` — any new test class **must** end in `Test` and live under `io.github.sinri.keel.logger.api` to be run.

## Versioning & Publishing

Version is in `gradle.properties` (`version=`). The build inspects the version string and selects the publish target automatically — do not override this logic:

- ends with `SNAPSHOT` → internal Nexus (snapshots URL)
- contains a `-<letter>` suffix (e.g. `5.0.0-rc.21`) → internal Nexus (releases URL)
- pure release (e.g. `5.0.0`) → Sonatype Central (OSSRH staging API), auto GPG-signed, then `closeAndReleaseStagingRepository`

`./gradlew publish` is the one-liner for all three cases. Snapshots/pre-releases are explicitly barred from Sonatype via task-enabling rules. Credentials (`sonatypeUsername/Password`, `internalNexus*`) live in `~/.gradle/gradle.properties`, not in the repo.

## Architecture

The design is interface-driven with template-method extension points. Read the AI integration guide (`docs/5.0.2/AI集成指南.md`) for the authoritative decision rules; the summary below covers the big picture.

### Core pipeline

`Logger` → builds a `SpecificLog` → hands to `LogWriterAdapter.accept(topic, log)`.

- `SpecificLogger<L>` (generic, `L extends SpecificLog<L>`) is the base interface; `Logger` is just `SpecificLogger<Log>`. `BaseSpecificLogger` implements the shared logic; `BaseLogger` specializes it for `Log`.
- `BaseLogger` does a **level pre-check** against `visibleLevel()` before constructing the log object — important for performance and for the semantics of `SilentLogger`/`SilentLogWriter` (see traps below).
- `Log` (concrete) extends `SpecificLog<Log>` (abstract builder base). Builders use `Consumer` style: `logger.info(log -> log.message(...).exception(e).context("k","v"))`. The per-level `Consumer` overloads set the level themselves — do **not** set the level inside the consumer.

### Levels

`LogLevel` enum: TRACE(100), DEBUG(200), INFO(300), NOTICE(400), WARNING(500), ERROR(600), FATAL(700), SILENT(Integer.MAX_VALUE). Comparison/filtering uses `severity()` **not** `ordinal()` — gaps are intentional so intermediate severities can be inserted later. Keep new level logic severity-based.

### Factories & global shared instance

`LoggerFactory` creates loggers and exposes one shared adapter. A process-wide singleton lives in `SharedLoggerFactoryHolder` (package-private) accessed via `LoggerFactory.getShared()` / `LoggerFactory.replaceShared(...)` (returns the previous instance). `BaseLoggerFactory` is the default; `SilentLoggerFactory` produces `SilentLogger`s.

### Adapter / writer layer

`LogWriterAdapter` is the contract (`accept(topic, log)`). `BaseLogWriter` is the singleton stdout default and implements `LogTextRender` (rendering logic is overridable). `SilentLogWriter` is a no-op adapter. `ThrowableRender` and `LoggingStackSpecification` control exception stack formatting — `LoggingStackSpecification.IgnorableCallStackPackageSet` is a mutable `Set<String>` of package prefixes (Vert.x, Netty, JDK, etc.) that get folded during stack rendering.

### Metrics

`MetricRecord` (use `MetricRecord.create(name, value, labels)`, impl `MetricRecordImpl`) + `MetricRecorder.recordMetric(...)`. This pipeline is **independent** of logging — there is no `LogWriterAdapter` API for metrics, and no default `MetricRecorder` implementation ships here.

### Not exported under JPMS

`ThrowableRender`, `MetricRecordImpl`, `SharedLoggerFactoryHolder` are intentionally not in `module-info` exports. Do not depend on them from caller code; treat them as internal.

## Conventions

- **No Lombok** anywhere in this repo or in code that extends it (per repo rules / AI guide). Write explicit accessors/builders.
- Javadoc is in Chinese and is the source of truth for API semantics — match the existing style and language when editing public types.
- `LateObject<T>` (Dart-style `late`) is a one-shot init container used internally; honor its set-once / read-after-set invariants.
- Versioned docs live under `docs/<version>/` (`5.0.1`, `5.0.2`, …) plus top-level `docs/*.md`. When changing public API, update the matching versioned doc set; the Jekyll site source is `./docs/`.
