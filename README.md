# Keel Logger API

Keel Logger API 是一个灵活且可扩展的 Java 日志 SDK，旨在提供结构化、上下文感知的日志记录能力。

## 特性

- **多级日志控制**：支持从 TRACE 到 FATAL 的 7 个日志级别，以及 SILENT 静默级别。
- **流式 API**：提供简洁的流式调用方式，便于构建包含丰富上下文的日志。
- **上下文感知**：支持在日志中携带上下文信息（Context），便于追踪和调试。
- **可扩展输出**：通过 `LogWriterAdapter` 接口，可以轻松扩展日志的输出目标（如控制台、文件、数据库等）。
- **结构化数据**：日志以 `SpecificLog` 结构体传递，包含时间戳、线程信息、异常堆栈等详细信息。

## 快速开始

### 引入依赖

在 Maven 项目的 `pom.xml` 中添加以下依赖：

```xml
<dependency>
    <groupId>io.github.sinri</groupId>
    <artifactId>keel-logger-api</artifactId>
</dependency>
```

### 基础使用

```java
void snippet() {
    // 假设你有一个实现了 Logger 接口的实例 logger
    logger.info("Hello, Keel Logger!");

    // 带上下文的日志
    logger.warning("Something happened", context -> {
        context.put("userId", 12345);
        context.put("action", "login");
    });

    // 记录异常
    try {
        // ...
    } catch (Exception e) {
        logger.exception(e, "An error occurred");
    }
}
```

## 文档

更多详细文档请参考 `docs` 目录：

- [设计与架构](docs/design.md)：了解 SDK 的设计目标、核心架构和关键概念。
- [使用指南](docs/usage.md)：详细的使用说明和示例。

## 许可证

本项目采用 GPL-v3.0 许可证。
