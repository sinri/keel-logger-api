# Keel Logger API — AI 集成指南

> **读者**：自动编程助手、代码代理、RAG 检索到的上下文。  
> **目标**：在最少试错的前提下，**正确调用**本库 API、**正确扩展**适配器与日志类型，并避开已知语义陷阱。

人类可读的设计说明见同目录 [概述与设计](./概述与设计.md)、[使用者指南](./使用者指南.md)、[扩展与集成](./扩展与集成.md)。

---

## 1. 执行任务前的固定事实（勿臆造）

- **语言**：Java **17+**。
- **Maven 坐标**：`io.github.sinri:keel-logger-api`（版本以项目/仓库为准，文档目录名为 `5.0.2` 表示系列）。
- **JPMS 模块名**：`io.github.sinri.keel.logger.api`；`module-info.java` 中 `requires io.github.sinri.keel.logger.api;`。
- **本库角色**：仅定义 **日志记录模型** 与 **输出契约**（`LogWriterAdapter`）；**不**内置 SLF4J/Log4j、**不**强制 JSON。**默认
  **输出为 `BaseLogWriter` → 标准输出文本。
- **指标**：`MetricRecord` / `MetricRecorder` 与日志管道 **独立**；本库不提供默认指标后端实现。
- **项目约定**：扩展或调用方代码 **不要使用 Lombok**（与仓库规则一致）。

---

## 2. 类型与包 — 速查（生成 import 时用）

| 用途        | 类型                                                          | 包                                         |
|-----------|-------------------------------------------------------------|-------------------------------------------|
| 通用 Logger | `Logger`, `BaseLogger`                                      | `io.github.sinri.keel.logger.api.logger`  |
| 泛型记录器     | `SpecificLogger<T>`, `BaseSpecificLogger<T>`                | 同上                                        |
| 通用一条日志    | `Log`                                                       | `io.github.sinri.keel.logger.api.log`     |
| 可扩展日志基类   | `SpecificLog<T>`                                            | 同上                                        |
| 键值上下文     | `LogContext`                                                | 同上                                        |
| 级别        | `LogLevel`                                                  | `io.github.sinri.keel.logger.api`         |
| 输出契约      | `LogWriterAdapter`, `BaseLogWriter`, `SilentLogWriter`      | `io.github.sinri.keel.logger.api.adapter` |
| 文本渲染（可继承） | `LogTextRender`（`BaseLogWriter` 已实现）                        | 同上                                        |
| 工厂        | `LoggerFactory`, `BaseLoggerFactory`, `SilentLoggerFactory` | `io.github.sinri.keel.logger.api.factory` |
| 指标        | `MetricRecord`, `MetricRecorder`                            | `io.github.sinri.keel.logger.api.metric`  |
| 堆栈折叠前缀集合  | `LoggingStackSpecification.IgnorableCallStackPackageSet`    | `io.github.sinri.keel.logger.api`         |

**不要引用**未导出实现类：`ThrowableRender`、`MetricRecordImpl`、`SharedLoggerFactoryHolder`（JPMS 下不可访问；语义上也不应依赖）。

---

## 3. 选择 API 的决策规则

1. **只需要字符串日志、类型为 `Logger`**  
   → `new BaseLogger("topic")` 或 `LoggerFactory` / `LoggerFactory.getShared().createLogger(Class)`。

2. **需要结构化构建（异常、多字段 context）**  
   → 使用 `logger.info(log -> …)` / `error(log -> …)` 等同级别 `Consumer` 重载，**不要**在 Consumer 内手动改级别（方法会覆盖为对应级别）。

3. **需要业务子类字段（审计等）**  
   → 定义 `class X extends SpecificLog<X>`，用 `protected extra(k,v)` 或自有字段；用
   `factory.createLogger(topic, X::new)` 得到 `SpecificLogger<X>`。

4. **需要统一输出到文件/Vert.x/远端**  
   → 实现 `LogWriterAdapter`，注入 `BaseLogger` 或自定义 `BaseLoggerFactory` 子类 + `LoggerFactory.replaceShared(...)`。

5. **需要完全不打日志且尽量少分配**  
   → `visibleLevel(LogLevel.SILENT)` 或使用 `SilentLogger`（注意：`SilentLogger.visibleLevel(LogLevel)` **会抛异常**）。

6. **需要调用路径存在但写入为空（测试/占位）**  
   → `SilentLogWriter` 或 `SilentLoggerFactory`；知悉：**默认 `BaseLogger` 仍可能在高级别下构造 `SpecificLog` 再交给空适配器
   **（与 `SILENT` 级别短路不同）。

7. **指标**  
   → `MetricRecord.create(...)` + 自行持有的 `MetricRecorder` 实现；**不要**试图通过 `LogWriterAdapter` 发送
   `MetricRecord`（无此 API）。

---

## 4. 推荐最小正确片段（可复制模板）

### 4.1 工厂 + 类名作 topic

```java
Logger logger = LoggerFactory.getShared().createLogger(MyClass.class);
logger.

visibleLevel(LogLevel.DEBUG);
logger.

info("started");
```

### 4.2 带上下文

```java
logger.warning("slow query",ctx ->ctx.

put("ms",elapsed).

put("sql",sqlHash));
```

### 4.3 异常 + context

```java
logger.error(log ->log
        .

message("handler failed")
    .

exception(e)
    .

context("requestId",requestId));
```

### 4.4 自定义适配器（骨架）

```java
public final class MyAdapter implements LogWriterAdapter {
    @Override
    public void accept(String topic, SpecificLog<?> log) {
        // 读取 log.timestamp(), log.level(), log.message(), log.context().toMap(), log.exception(), log.extra()
    }
}
```

### 4.5 自定义 `SpecificLog`（骨架）

```java
public final class AuditLog extends SpecificLog<AuditLog> {
    public AuditLog actor(String id) {
        return extra("actor", id);
    }
}
// SpecificLogger<AuditLog> log = factory.createLogger("audit", AuditLog::new);
```

---

## 5. 必须避免的常见错误

| 错误做法                                                                                  | 原因                                                                     | 正确做法                               |
|---------------------------------------------------------------------------------------|------------------------------------------------------------------------|------------------------------------|
| 调用 `message(LogLevel level, String msg, Object... args)` 并假设 `log.level()` == `level` | 该方法只做可见性判断，**未**对 `SpecificLog` 调用 `level(level)`，记录内默认级别仍为构造时的 `INFO` | 用 `debug/info/...` 或 `Consumer` 重载 |
| 把 `SilentLoggerFactory` 等同于「零分配」                                                      | 工厂创建的是普通 `BaseLogger`，仅适配器为空                                           | 需要短路时用 `SILENT` 或 `SilentLogger`   |
| 在子类中公开调用父类 `extra` 以外的随意 Map                                                          | `extra()` 返回内部 map；若需稳定序列化契约应明确字段                                      | 优先 `extra(k,v)` 或明确字段 + getter     |
| 假设 `MetricRecord.labels()` 不可变                                                        | 返回内部可变 map                                                             | 集成实现若需快照则拷贝                        |
| 多线程共享 `LateObject` 且无外部同步                                                             | `set/get` 非并发语义；`ensure` 的 supplier 可能多次调用                             | 按文档仅在单线程或外部同步下使用                   |

---

## 6. 级别语义（生成条件日志时核对）

- `LogLevel` 严重度顺序：`TRACE` < `DEBUG` < `INFO` < `NOTICE` < `WARNING` < `ERROR` < `FATAL` < `SILENT`；
  `severity()` 依次为 `100`…`700`（步长 `100`），`SILENT` 为 `Integer.MAX_VALUE`（非 `ordinal()`）。完整表见《概述与设计》§3.1。
- `visibleLevel` 为 **最低可见**：记录的级别 **不低于** 该阈值时才构建并 `accept`。
- `SILENT`：**不**向适配器投递（与「适配器 no-op」不同）。

---

## 7. 修改本仓库时的自检清单（AI 改 keel-logger-api 源码用）

- [ ] 未使用 Lombok。
- [ ] 公开 API 变更已更新 `module-info.java` 的 `exports`（若新增包）。
- [ ] `SpecificLogger` 新便捷方法是否应 **先** 做级别检查再 `get()` 日志实例（与现有 `logIfEnabled` 一致）。
- [ ] 新增 `static` 可变全局状态需文档化（类似 `IgnorableCallStackPackageSet`）。
- [ ] 若修改 `LogLevel` 的 `severity` 取值，已同步 `概述与设计.md` §3.1 表格及 `使用者指南` / `README` 中的相关描述。
- [ ] 运行 `./gradlew test`。

---

## 8. 检索关键词（供嵌入向量库 / 标题）

`Keel Logger API`, `LogWriterAdapter`, `SpecificLog`, `SpecificLogger`, `BaseLogger`, `LoggerFactory.getShared`,
`replaceShared`, `LogLevel SILENT`, `MetricRecorder`, `BaseLogWriter`, `LogTextRender`,
`LoggingStackSpecification`, JPMS `io.github.sinri.keel.logger.api`.
