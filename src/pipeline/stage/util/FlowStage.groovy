package pipeline.stage.util

import pipeline.common.interfaces.IActionMain
import pipeline.common.util.Config

abstract class FlowStage extends Base implements IActionMain {
    protected boolean entryBuildFlow = true

    abstract void main(Config config)

    abstract String getStageDescribe()

    void run(Config config) {
        if (entryBuildFlow) {
            stage(stageDescribe) {
                main(config)
            }
        } else {
            main(config)
        }
    }

    def entryDeployMode() {
        this.entryBuildFlow = false
        return this
    }
}
