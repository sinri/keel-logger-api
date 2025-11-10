package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.LogLevel;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

class IssueRecorderTest {
    private final TestIssueRecorder issueRecorder;

    public IssueRecorderTest() {
        issueRecorder = new TestIssueRecorder();
    }

    @Test
    public void test() {
        issueRecorder.recordIssue(x -> x.raw("test"));
    }

    private static class TestIssueRecord extends IssueRecord<TestIssueRecord> {
        private String raw;

        @Override
        public TestIssueRecord getImplementation() {
            return this;
        }

        public TestIssueRecord raw(String raw) {
            this.raw = raw;
            return this;
        }
    }

    private static class TestIssueRender implements IssueRecordRender<TestIssueRecord, String> {

        @Nonnull
        @Override
        public String render(@Nonnull String topic, @Nonnull TestIssueRecord loggingEntity) {
            return loggingEntity.raw;
        }
    }

    private static class TestIssueRecorder implements IssueRecorder<TestIssueRecord, String> {
        private final Supplier<TestIssueRecord> supplier;
        private final IssueRecordRender<TestIssueRecord, String> render;

        public TestIssueRecorder() {
            this.render = new TestIssueRender();
            this.supplier = TestIssueRecord::new;
        }

        @Nonnull
        @Override
        public Supplier<TestIssueRecord> issueRecordSupplier() {
            return this.supplier;
        }

        @Nonnull
        @Override
        public IssueRecordRender<TestIssueRecord, String> render() {
            return render;
        }

        @Nonnull
        @Override
        public LogLevel visibleLevel() {
            return LogLevel.INFO;
        }

        @Nonnull
        @Override
        public String topic() {
            return "TEST";
        }

        @Override
        public void recordIssue(@Nonnull TestIssueRecord issueRecord) {
            System.out.println(render.render(topic(), issueRecord));
        }
    }
}