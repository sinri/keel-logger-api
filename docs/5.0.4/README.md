# Keel Logger API 5.0.4 文档

本目录收录 **Keel Logger API**（`io.github.sinri:keel-logger-api`）在 **5.0.x** 系列中的设计与使用说明，面向两类读者：

- **使用者**：在 Keel 应用或其它 Java 模块中调用日志与指标 API 的业务与基础设施开发者。
- **Keel 框架开发者**：实现适配器、接入统一日志后端、或在本库之上提供框架级封装的维护者。
- **AI / 自动编程代理**：见 [AI 集成指南](./AI集成指南.md)（结构化约束、决策规则、陷阱表、可检索关键词）。

## 版本变更（5.0.3 → 5.0.4）

### `LateObject` 强化

- `set` 与 `ensure` 在并发下共享一次赋值约束，`ensure` 的 supplier 成功初始化时只执行一次。
- `set`、supplier 及 supplier 返回值不再接受 `null`，防止非空类型承诺和实际状态不一致。
- supplier 抛出的运行时异常或 `Error` 现在原样传播，不再统一包装为 `RuntimeException`。
- 拒绝 supplier 内部对同一 `LateObject` 执行重入 `set` / `ensure`，不再允许绕过“仅赋值一次”约束。
- `ensure` 接受 `Supplier<? extends T>`，允许使用协变的 supplier。

### 并发修复

- `BaseSpecificLogger` 的 normalized logger 首次懒创建与 `visibleLevel(...)` 更新现在使用同一互斥边界，
  避免并发时 normalized logger 永久保留旧级别。

### 构建依赖

- 测试依赖从未实际使用的 `io.vertx:vertx-junit5` 改为直接依赖 `org.junit.jupiter:junit-jupiter:5.14.0`。
- 测试运行时不再额外引入 Vert.x Core、Netty 和 Jackson；本库生产代码与发布 POM 仍不依赖 Vert.x。

### 迁移指南

> 现有正常用法无需调整。若代码依赖 `ensure` 将 supplier 异常包装成新的 `RuntimeException`，
> 应改为处理 supplier 原始抛出的异常类型。supplier 必须返回非空值，且不得重入操作同一 `LateObject`。

## 文档结构

| 文档                           | 说明                                                                                       |
|--------------------------------|--------------------------------------------------------------------------------------------|
| [AI 集成指南](./AI集成指南.md) | 面向 LLM：固定事实、类型速查、API 决策规则、模板代码、常见错误表、改库自检清单、检索关键词 |
| [概述与设计](./概述与设计.md)  | 定位、Java 模块边界、包结构、核心概念与数据流                                              |
| [使用者指南](./使用者指南.md)  | 依赖、Logger/工厂、级别与过滤、上下文与异常、指标、`message(level, …)` 行为说明            |
| [扩展与集成](./扩展与集成.md)  | `LogWriterAdapter`、`LogTextRender`、自定义 `SpecificLog`、全局工厂、沉默模式、堆栈折叠    |

## 版本与构建

- **语言与运行时**：Java 17+。
- **JPMS**：模块名 `io.github.sinri.keel.logger.api`（见仓库根目录 `module-info.java`）。
- **空安全标注**：编译期可选依赖 `org.jspecify:jspecify`（`requires static`），便于静态分析，运行时不必引入。

具体 Maven 坐标以发布仓库为准；依赖与示例见本目录 [使用者指南](./使用者指南.md)；构建与发布流程见仓库根目录
`README.md` 中「开发要约」。

## 与 Keel 框架的关系

本库是 **日志与指标的 API 契约层**：定义记录什么、如何表达（`SpecificLog`、上下文、级别），以及输出由谁完成（
`LogWriterAdapter`）。Keel 核心或其它模块可提供 Vert.x、文件、远端等具体 `LogWriterAdapter` /
`MetricRecorder` 实现，应用代码则尽量只依赖本 API，便于测试与替换实现。
