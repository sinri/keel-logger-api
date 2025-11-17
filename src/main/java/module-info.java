module io.github.sinri.keel.logger.api {
    requires transitive org.jetbrains.annotations;

    exports io.github.sinri.keel.logger.api;
    exports io.github.sinri.keel.logger.api.logger;
    exports io.github.sinri.keel.logger.api.metric;
    exports io.github.sinri.keel.logger.api.adapter;
    exports io.github.sinri.keel.logger.api.factory;
    exports io.github.sinri.keel.logger.api.log;
}