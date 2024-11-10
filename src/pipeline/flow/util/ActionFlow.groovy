package pipeline.flow.util

import pipeline.common.constants.EStagePathType
import pipeline.common.interfaces.IActionMain
import pipeline.common.util.Config
import pipeline.common.util.PrepareStage
import pipeline.stage.util.FlowStage

class ActionFlow extends Base {

    ActionFlow(Config config) {
        super(config)
    }

    void stageRun() {
        for (stageTuple in stageTupleList) {
            try {
                IActionMain flowStage = PrepareStage.createStage(stageTuple) as FlowStage
                flowStage.run(config)
            }
            catch (Exception e) {
                EchoStep("${stageTuple.stageName} Stage execute failed.\n${e}")
                throw e
            }
        }
    }

    protected void prepareStageList() {
        stageListAddToStageTupleList(EStagePathType.flow, stageList)
    }
}
