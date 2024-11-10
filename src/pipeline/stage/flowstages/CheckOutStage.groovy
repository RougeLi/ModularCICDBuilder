package pipeline.stage.flowstages

import pipeline.common.constants.SCM
import pipeline.common.scm.Git
import pipeline.common.util.Config
import pipeline.stage.util.Stage

class CheckOutStage extends Stage {
    public String STAGE_NAME = 'Checkout'
    private boolean entryBuildFlow = true

    void main(Config config) {
        stage(STAGE_NAME) {
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

    CheckOutStage entryDeployMode() {
        this.entryBuildFlow = false
        return this
    }
}
