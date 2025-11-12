package io.github.sinri.keel.logger.base.adapter.render;

import io.github.sinri.keel.logger.api.event.Event2LogRender;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseEvent2LogRender implements Event2LogRender {
    @Override
    public String renderThrowable(@Nonnull Throwable throwable) {
        try (StringWriter stringWriter = new StringWriter()) {
            try (PrintWriter printWriter = new PrintWriter(stringWriter)) {
                throwable.printStackTrace(printWriter);
                return stringWriter.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String renderClassification(@Nonnull List<String> classification) {
        return String.join(",", classification);
    }

    @Override
    public String renderContext(@Nonnull Map<String, Object> map) {
        if (!map.isEmpty()) {
            return map.entrySet().stream()
                      .map(entry -> entry.getKey() + ":" + entry.getValue())
                      .collect(Collectors.joining("\n"));
        } else {
            return "";
        }
    }
}
