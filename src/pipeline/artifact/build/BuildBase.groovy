package pipeline.artifact.build

import pipeline.Pipeline
import pipeline.common.util.Config
import pipeline.flow.util.ActionFlow

abstract class BuildBase extends Pipeline {
    protected Config config
    protected ActionFlow buildFlow = null

    BuildBase(Config config) {
        this.config = config
    }

    abstract void platformArgInit()

    void main() {
        platformArgInit()
    }

    ActionFlow getActionFlow() {
        return buildFlow
    }
}
