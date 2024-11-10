package pipeline.stage.flow

import pipeline.common.constants.SCM
import pipeline.common.scm.Git
import pipeline.common.util.Config
import pipeline.stage.util.FlowStage

class SCMCheckOut extends FlowStage {
    String stageDescribe = 'SCM Checkout'

    void main(Config config) {
        EchoStep("SCM: ${config.SCM}, BUILD_PLATFORM: ${config.BUILD_PLATFORM}")
        switch (config.SCM) {
            case SCM.Git:
                Git.checkout(config, entryBuildFlow)
                break
            default:
                EchoStep("Not support SCM: ${config.SCM}")
                break
        }
    }
}
