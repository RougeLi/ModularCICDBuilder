package pipeline.artifact.build


import pipeline.common.util.Config

class DefaultBuildPlatform extends BuildBase {
    DefaultBuildPlatform(Config config) {
        super(config)
    }

    void platformArgInit() {
        config.PROJECT_LABEL = ''
    }
}
