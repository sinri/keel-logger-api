package io.github.sinri.keel.logger.api.log;

import javax.annotation.Nonnull;

class EmbeddedLogRecorder implements LogRecorder {
    private final static EmbeddedLogRecorder instance = new EmbeddedLogRecorder();

    private EmbeddedLogRecorder() {
    }

    public static EmbeddedLogRecorder getInstance() {
        return instance;
    }

    @Override
    public void recordLog(@Nonnull LogRecord record) {
        System.out.println(record);
    }
}
