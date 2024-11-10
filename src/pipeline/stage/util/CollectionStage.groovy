package pipeline.stage.util

import pipeline.common.interfaces.ICustomActionMain
import pipeline.common.util.Config

abstract class CollectionStage extends Base implements ICustomActionMain {

    void run(Config config, StageData stageData) {
        main(config, stageData)
    }
}
