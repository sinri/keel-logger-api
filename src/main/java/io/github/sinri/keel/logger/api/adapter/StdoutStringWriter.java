package io.github.sinri.keel.logger.api.adapter;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
public final class StdoutStringWriter implements LogWriter<String> {
    private final static StdoutStringWriter instance = new StdoutStringWriter();

    private StdoutStringWriter() {
    }

    public static StdoutStringWriter getInstance() {
        return instance;
    }

    @Override
    public void write(@Nonnull String renderedEntity) {
        System.out.println(renderedEntity);
    }

    @Override
    public void close() {

    }
}
