package io.github.sinri.keel.logger.base.adapter.writer;

import io.github.sinri.keel.logger.api.adapter.InstantLogWriter;

import javax.annotation.Nonnull;

/**
 * @since 5.0.0
 */
public class BaseStringToStdoutWriter implements InstantLogWriter<String> {
    private final static BaseStringToStdoutWriter instance = new BaseStringToStdoutWriter();

    private BaseStringToStdoutWriter() {
    }

    public static BaseStringToStdoutWriter getInstance() {
        return instance;
    }

    @Override
    public void write(@Nonnull String topic, @Nonnull String renderedEntity) {
        System.out.println(renderedEntity);
    }
}
