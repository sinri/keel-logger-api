package io.github.sinri.keel.logger.api.issue;

import io.github.sinri.keel.logger.api.render.Render;

/**
 * @param <T> the type of issue record
 * @param <R> the type of rendered entity
 * @since 5.0.0
 */
public interface IssueRecordRender<T extends IssueRecord<T>, R> extends Render<T, R> {
}
