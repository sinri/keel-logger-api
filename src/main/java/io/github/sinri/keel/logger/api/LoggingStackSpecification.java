package io.github.sinri.keel.logger.api;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 5.0.0
 */
public final class LoggingStackSpecification {
    @NotNull
    public static final Set<String> IgnorableCallStackPackage;

    static {
        IgnorableCallStackPackage = new HashSet<>(Set.of(
                "io.github.sinri.keel.facade.async.",
                "io.github.sinri.keel.facade.tesuto.",
                "io.vertx.core.",
                "io.vertx.ext.web",
                "io.netty.",
                "java.lang.",
                "jdk.internal.",
                "io.vertx.mysqlclient",
                "io.vertx.sqlclient"
        ));
    }
}
