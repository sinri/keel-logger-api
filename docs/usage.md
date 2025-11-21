# 使用指南

本文档将指导您如何集成和使用 Keel Logger API。

## 1. 引入依赖

首先，在您的 Maven 项目中添加依赖：

```xml

<dependency>
    <groupId>io.github.sinri</groupId>
    <artifactId>keel-logger-api</artifactId>
</dependency>
```

## 2. 初始化配置

在使用日志之前，您需要配置日志的输出方式（Adapter）和工厂（Factory）。

### 2.1 实现 LogWriterAdapter

`LogWriterAdapter` 决定了日志输出到哪里。您可以简单地输出到控制台，或者写入文件、数据库等。

```java
import io.github.sinri.keel.logger.api.adapter.LogWriterAdapter;
import io.github.sinri.keel.logger.api.log.SpecificLog;

public class ConsoleLogWriter implements LogWriterAdapter {
    @Override
    public void accept(String topic, SpecificLog<?> log) {
        System.out.println("[" + log.level() + "] " + topic + ": " + log.message());
        if (log.exception() != null) {
            log.exception().printStackTrace();
        }
    }
}
```

### 2.2 创建 LoggerFactory

使用 `BaseLoggerFactory` 或自定义工厂来管理 Logger 实例。

```java
import io.github.sinri.keel.logger.api.factory.BaseLoggerFactory;
import io.github.sinri.keel.logger.api.logger.Logger;

// 创建工厂实例
BaseLoggerFactory factory = new BaseLoggerFactory() {
    @Override
    public LogWriterAdapter sharedAdapter() {
        return new ConsoleLogWriter();
    }
};

        // 创建 Logger
        Logger logger = factory.createLogger("MyService");
```

## 3. 日志记录

### 3.1 基础日志

```
    logger.info("系统启动成功");
    logger.warning("磁盘空间不足");
    logger.error("连接数据库失败");
```

### 3.2 带上下文的日志

在微服务或复杂业务中，上下文信息（如 UserID, RequestID）非常重要。

```
    logger.info("处理订单",context ->{
        context.put("orderId","ORD-20231027-001");
        context.put("userId",10086);
    });
```

### 3.3 异常记录

记录异常时，可以使用 `exception` 方法，SDK 会自动处理堆栈信息。

```
    try{
        // 业务逻辑
    }catch(Exception e){
        logger.exception(e, "处理请求时发生未知错误");
    
        // 或者带上下文
        logger.exception(e, "处理请求时发生未知错误",ctx ->{
            ctx.put("requestId","REQ-001");
        });
    }
```

### 3.4 流式 API (Fluent API)

对于更复杂的日志构建需求，可以使用流式 API。

```
    logger.log(log ->log
        .level(LogLevel.NOTICE)
        .message("自定义日志")
        .context(ctx ->ctx.put("key","value"))
    );
```

## 4. 高级特性

### 4.1 堆栈过滤

为了让异常堆栈更清晰，您可以配置 `LoggingStackSpecification` 来忽略某些框架的调用栈。

```java
// 默认已经包含了一些常见的框架包，如 io.vertx, io.netty 等
// 您可以根据需要修改或扩展
```

### 4.2 自定义 SpecificLog

### 4.2 自定义 SpecificLog

如果您需要记录特定的业务数据结构（例如 HTTP 请求日志），可以继承 `SpecificLog`。

#### 1. 定义 Log 类

```java
import io.github.sinri.keel.logger.api.log.SpecificLog;

public class AccessLog extends SpecificLog<AccessLog> {
    private String ip;
    private String method;
    private String path;
    private int statusCode;

    public AccessLog ip(String ip) {
        this.ip = ip;
        return getImplementation();
    }

    public AccessLog method(String method) {
        this.method = method;
        return getImplementation();
    }

    public AccessLog path(String path) {
        this.path = path;
        return getImplementation();
    }

    public AccessLog statusCode(int statusCode) {
        this.statusCode = statusCode;
        return getImplementation();
    }

    // Getters...
    public String ip() {
        return ip;
    }

    public String method() {
        return method;
    }

    public String path() {
        return path;
    }

    public int statusCode() {
        return statusCode;
    }
}
```

#### 2. 使用自定义 Log

您可以使用 `LoggerFactory` 创建针对该 Log 类型的 Logger。

```
    // 创建 Logger，指定 AccessLog 的构造器
    SpecificLogger<AccessLog> accessLogger = factory.createLogger("AccessLog", AccessLog::new);
    
    // 使用流式 API 记录日志
    accessLogger.info(log ->log
            .ip("192.168.1.1")
        .method("GET")
        .path("/api/users/1")
        .statusCode(200)
        .message("Request processed")
    );
```

#### 3. 配合自定义 Adapter

为了输出这些自定义字段，您可能需要自定义 `LogWriterAdapter`。

```java
public class AccessLogWriter implements LogWriterAdapter {
    @Override
    public void accept(String topic, SpecificLog<?> log) {
        if (log instanceof AccessLog accessLog) {
            // 处理 AccessLog 特有的字段
            System.out.printf("[%s] %s %s %s %d\n",
                    topic, accessLog.ip(), accessLog.method(), accessLog.path(), accessLog.statusCode());
        } else {
            // 处理普通日志
            System.out.println("[" + topic + "] " + log.message());
        }
    }
}
```
