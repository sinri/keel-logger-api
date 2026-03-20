# Keel Logger API 5.0.1 文档

本目录收录 **Keel Logger API**（`io.github.sinri:keel-logger-api`）在 **5.0.x** 系列中的设计与使用说明，面向两类读者：

- **使用者**：在 Keel 应用或其它 Java 模块中调用日志与指标 API 的业务与基础设施开发者。
- **Keel 框架开发者**：实现适配器、接入统一日志后端、或在本库之上提供框架级封装的维护者。
- **AI / 自动编程代理**：见 [AI 集成指南](./AI集成指南.md)（结构化约束、决策规则、陷阱表、可检索关键词）。

## 文档结构

| 文档                     | 说明                                                                  |
|------------------------|---------------------------------------------------------------------|
| [AI 集成指南](./AI集成指南.md) | 面向 LLM：固定事实、类型速查、API 决策规则、模板代码、常见错误表、改库自检清单、检索关键词                   |
| [概述与设计](./概述与设计.md)    | 定位、Java 模块边界、包结构、核心概念与数据流                                           |
| [使用者指南](./使用者指南.md)    | 依赖、Logger/工厂、级别与过滤、上下文与异常、指标、`message(level, …)` 行为说明               |
| [扩展与集成](./扩展与集成.md)    | `LogWriterAdapter`、`LogTextRender`、自定义 `SpecificLog`、全局工厂、沉默模式、堆栈折叠 |

## 版本与构建

- **语言与运行时**：Java 17+。
- **JPMS**：模块名 `io.github.sinri.keel.logger.api`（见仓库根目录 `module-info.java`）。
- **空安全标注**：编译期可选依赖 `org.jspecify:jspecify`（`requires static`），便于静态分析，运行时不必引入。

具体 Maven 坐标以发布仓库为准；构建与发布流程可参考仓库根目录 `README.md`。

## 与 Keel 框架的关系

本库是 **日志与指标的 API 契约层**：定义记录什么、如何表达（`SpecificLog`、上下文、级别），以及输出由谁完成（
`LogWriterAdapter`）。Keel 核心或其它模块可提供 Vert.x、文件、远端等具体 `LogWriterAdapter` /
`MetricRecorder` 实现，应用代码则尽量只依赖本 API，便于测试与替换实现。
