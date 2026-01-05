package io.github.sinri.keel.logger.api;

import org.jspecify.annotations.NullMarked;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 5.0.0
 */
@NullMarked
public final class LoggingStackSpecification {
    /**
     * Modify the elements inside if needed.
     */
    public static final Set<String> IgnorableCallStackPackageSet;

    static {
        IgnorableCallStackPackageSet = new HashSet<>(Set.of(
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
