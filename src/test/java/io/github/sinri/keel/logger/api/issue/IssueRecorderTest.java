package io.github.sinri.keel.logger.api.issue;

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
        public String render(@Nonnull TestIssueRecord loggingEntity) {
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
        public Supplier<TestIssueRecord> getIssueRecordSupplier() {
            return this.supplier;
        }

        @Nonnull
        @Override
        public IssueRecordRender<TestIssueRecord, String> getIssueRecordRender() {
            return render;
        }

        @Override
        public void recordIssue(@Nonnull TestIssueRecord issueRecord) {
            System.out.println(render.render(issueRecord));
        }
    }
}