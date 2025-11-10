package io.github.sinri.keel.logger.api;

import javax.annotation.Nonnull;

public enum LogLevel {
    TRACE, DEBUG, INFO, NOTICE, WARNING, ERROR, FATAL, SILENT;

    public boolean isEnoughSeriousAs(@Nonnull LogLevel standardLevel) {
        return this.ordinal() >= standardLevel.ordinal();
    }

    public boolean isNegligibleThan(@Nonnull LogLevel standardLevel) {
        return this.ordinal() < standardLevel.ordinal();
    }

    /**
     * @return should always be silent
     * @since 1.10
     */
    public boolean isSilent() {
        return this.ordinal() >= SILENT.ordinal();
    }
}
