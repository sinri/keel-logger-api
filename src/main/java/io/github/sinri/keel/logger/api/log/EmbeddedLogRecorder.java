package io.github.sinri.keel.logger.api.log;

import javax.annotation.Nonnull;

class EmbeddedLogRecorder implements LogRecorder<String> {
    private final static EmbeddedLogRecorder instance = new EmbeddedLogRecorder();

    private EmbeddedLogRecorder() {
    }

    public static EmbeddedLogRecorder getInstance() {
        return instance;
    }

    @Override
    public LogRecordRender<String> getLogRecordRender() {
        return EmbeddedLogRecordRender.getInstance();
    }

    @Override
    public void recordLog(@Nonnull LogRecord record) {
        var s = getLogRecordRender().render(record);
        System.out.println(s);
    }
}
