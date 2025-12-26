# Switch to Gradle

## Prepare Gradle System Files

### ~/.gradle/gradle.properties

略

## Prepare Gradle Project Files

### build.gradle.kts

略

### gradle.properties

略

### settings.gradle.kts

略

## Generate Gradle Wrapper

Run the following command in the project root directory to generate a Gradle wrapper.

```bash
gradle wrapper --gradle-version 9.2.1 --distribution-type all
```

## Usage

* clean: `./gradlew clean`
* compile: `./gradlew compileJava`
* test: `./gradlew test`
* package: `./gradlew assemble`
* install: `./gradlew build`
* deploy:
    * internal: `./gradlew publishToInternal`
    * release: `./gradlew publishToRelease`
* dependency:tree: `./gradlew dependencies`
* help:describe: `./gradlew tasks`
