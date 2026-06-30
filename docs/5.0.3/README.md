# Keel Logger API 5.0.3 文档

本目录收录 **Keel Logger API**（`io.github.sinri:keel-logger-api`）在 **5.0.x** 系列中的设计与使用说明，面向两类读者：

- **使用者**：在 Keel 应用或其它 Java 模块中调用日志与指标 API 的业务与基础设施开发者。
- **Keel 框架开发者**：实现适配器、接入统一日志后端、或在本库之上提供框架级封装的维护者。
- **AI / 自动编程代理**：见 [AI 集成指南](./AI集成指南.md)（结构化约束、决策规则、陷阱表、可检索关键词）。

## 版本变更（5.0.2 → 5.0.3）

### 修复

- **`SpecificLogger.message(LogLevel, String, Object...)` 现在正确携带级别**：此前该方法仅用 `level` 做可见性判断，
  交给适配器的 `SpecificLog` 未调用 `level(level)`，记录内级别恒为构造默认的 `INFO`，导致下游按 `log.level()` 渲染或
  路由时与传入 `level` 不一致。现已补上 `.level(level)`。
- **`SilentLoggerFactory` 现在真正沉默**：此前 `createLogger(...)` 返回的是普通 `BaseLogger` / `BaseSpecificLogger`
  （默认 `visibleLevel = INFO`），仅适配器为空操作——`INFO` 及以上日志仍会完整构造 `SpecificLog` 并执行用户
  `Consumer` 后才被空适配器丢弃。现改为：
  - `createLogger(topic)` 返回 `SilentLogger`（`visibleLevel()` 恒为 `SILENT`，所有级别在前置检查处短路，零分配）；
  - `createLogger(topic, supplier)` 构造后置 `visibleLevel(SILENT)`，同样在前置检查处短路。
- **`SilentLogger` 现可通过公开静态工厂 `SilentLogger.create(topic)` 构造**：此前其构造器为私有且无公开构造路径，
  仅能反射访问。

### 依赖

- `vertxVersion` 从 `5.0.12` 升级至 `5.1.3`。

### 迁移指南

> 本次为行为修正，**API 形态兼容**（无签名破坏）。
> - 若你的代码曾因 `message(LogLevel, …)` 不带级别而**绕开**它、改用 `info/error/...` 或 `Consumer` 重载，现在该方法的级别
>   语义已正确，可按需恢复使用；原有替代写法继续有效。
> - 若你依赖"`SilentLoggerFactory` 仍会构造日志对象"这一旧有行为（极少见），需注意现在它在 `SILENT` 处短路、不再分配。
>   若确需"调用路径存在但写入为空且不短路"，请改用 `new BaseLogger(topic, SilentLogWriter.getInstance())` 自行构造。

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

具体 Maven 坐标以发布仓库为准；依赖与示例见本目录 [使用者指南](./使用者指南.md)；构建与发布流程见仓库根目录
`README.md` 中「开发要约」。

## 与 Keel 框架的关系

本库是 **日志与指标的 API 契约层**：定义记录什么、如何表达（`SpecificLog`、上下文、级别），以及输出由谁完成（
`LogWriterAdapter`）。Keel 核心或其它模块可提供 Vert.x、文件、远端等具体 `LogWriterAdapter` /
`MetricRecorder` 实现，应用代码则尽量只依赖本 API，便于测试与替换实现。
