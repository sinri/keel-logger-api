package io.github.sinri.keel.logger.api.adapter;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
public final class StringToStdoutWriter implements LogWriter<String> {
    private final static StringToStdoutWriter instance = new StringToStdoutWriter();

    private StringToStdoutWriter() {
    }

    public static StringToStdoutWriter getInstance() {
        return instance;
    }

    @Override
    public void close() {

    }

    @Override
    public void write(@Nonnull String topic, @Nonnull String renderedEntity) {
        System.out.println(renderedEntity);
    }
}
