package pipeline.artifact.ci

import pipeline.Pipeline
import pipeline.common.constants.BuildTypeLabel
import pipeline.common.util.Config
import pipeline.flow.util.ActionFlow

abstract class BuildBase extends Pipeline {
    protected Config config
    protected ActionFlow ciFlow = null

    BuildBase(Config config) {
        this.config = config
    }

    abstract void platformArgInit()

    void main() {
        platformArgInit()
    }

    ActionFlow getCIFlow() {
        return ciFlow
    }

    protected String getClientBuildLabel() {
        switch (config.BUILD_TYPE) {
            case BuildTypeLabel.PUBLISH:
                return 'publish_win'
            case BuildTypeLabel.CONTINUOUS:
                return 'continuous_win_build'
            default:
                return 'windows'
        }
    }
}
