# 设计与架构

本文档详细介绍了 Keel Logger API 的设计目标、核心架构以及关键概念。

## 设计目标

Keel Logger API 的设计初衷是为了满足现代 Java 应用对日志记录的复杂需求，主要包括以下几个目标：

1. **解耦与抽象**：将日志的记录（Logger）与日志的输出（Writer）分离，使得业务代码只需关注“记录什么”，而无需关心“输出到哪里”。
2. **上下文感知**：在微服务和异步编程模型中，追踪请求的上下文至关重要。本 SDK 原生支持在日志中携带丰富的上下文信息。
3. **流式 API**：提供流畅的链式调用接口，使得构建复杂日志内容（如添加多个上下文键值对）变得简单且代码可读性高。
4. **结构化数据**：日志不仅仅是文本字符串，而是包含时间戳、线程、级别、异常堆栈等信息的结构化数据 (`SpecificLog`)，便于后续的分析和处理。

## 核心架构

SDK 的核心架构主要由以下几个组件构成：

### 1. Logger (日志记录器)

`Logger` 是用户直接交互的接口，继承自 `SpecificLogger`。它定义了日志记录的行为。

- **SpecificLogger**：提供了针对特定日志记录类型 (`SpecificLog`) 的操作接口。它包含了一系列便捷方法（如 `info`,
  `error` 等）以及流式构建方法。
- **BaseLogger** / **BaseSpecificLogger**：提供了 `Logger` 接口的基础实现，处理了通用的逻辑，如日志级别的判断。

### 2. SpecificLog (日志数据载体)

`SpecificLog` 是日志信息的载体，它包含以下核心字段：

- `level`: 日志严重性等级 (`LogLevel`)。
- `message`: 日志消息文本。
- `timestamp`: 记录时间戳。
- `threadInfo`: 记录日志的线程信息。
- `logContext`: 上下文信息 (`LogContext`)，本质上是一个键值对集合。
- `exception`: 关联的异常信息 (`Throwable`)。
- `extra`: 额外的扩展字段。

### 3. LogWriterAdapter (日志输出适配器)

`LogWriterAdapter` 负责将 `SpecificLog` 输出到具体的目的地。这是一个函数式接口 (`Consumer<SpecificLog<?>>`)，允许用户自定义输出逻辑。

- **InstantLogWriterAdapter**: 立即输出日志的适配器。
- **PersistentLogWriterAdapter**: 用于持久化日志的适配器。

## 关键概念

### 日志等级 (LogLevel)

SDK 定义了 8 个日志等级，按严重性递增：

1. **TRACE**: 追踪信息，最详细的调试信息。
2. **DEBUG**: 调试信息，用于开发阶段调试。
3. **INFO**: 一般信息，记录系统的正常运行状态。
4. **NOTICE**: 注意信息，正常的但重要的事件。
5. **WARNING**: 警告信息，可能出现问题但不影响系统运行。
6. **ERROR**: 错误信息，系统发生了错误。
7. **FATAL**: 致命错误，系统可能无法继续运行。
8. **SILENT**: 静默级别，用于关闭日志记录（不可作为日志记录的级别，仅用于设置可见性）。

### 流式 API (Fluent API)

为了简化日志记录，SDK 提供了流式 API。例如：

```
logger.log(log ->log
        .level(LogLevel.INFO)
        .message("User login")
        .context(ctx ->ctx.put("userId",1001))
        );
```

这种方式允许在一条语句中完成日志的所有配置，清晰且易于维护。

### 堆栈过滤 (Stack Trace Filtering)

为了减少日志中无关的堆栈信息，SDK 提供了
`LoggingStackSpecification` 类，用于定义需要忽略的包名。这在打印异常堆栈时非常有用，可以过滤掉框架内部的调用栈，突出业务代码的调用路径。
