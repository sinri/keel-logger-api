# Switch to Gradle

## Prepare Gradle System Files

### ~/.gradle/gradle.properties

Ensure the following properties are set.

```properties
internalNexusPublicUrl=*
internalNexusSnapshotsUrl=*
internalNexusReleasesUrl=*
internalNexusUsername=*
internalNexusPassword=*
```

## Prepare Gradle Project Files

### build.gradle.kts

See the `build.gradle.kts` file.

### gradle.properties

See the `gradle.properties` file.

### settings.gradle.kts

See the `settings.gradle.kts` file.

## Generate Gradle Wrapper

Run the following command in the project root directory to generate a Gradle wrapper.

The gradle version should be the same as the one used in the project.

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

## VSCode Switch Issue

1. 重新加载 Java 语言服务器：

按 Cmd+Shift+P
输入 `Java: Clean Java Language Server Workspace`
选择 Restart and delete

2. 重新导入项目：

按 Cmd+Shift+P
输入 Java: Reload Projects

3. 完全重启 VSCode