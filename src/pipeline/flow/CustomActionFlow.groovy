package pipeline.flow

import pipeline.common.constants.EStagePathType
import pipeline.common.util.Config
import pipeline.common.util.StageTuple
import pipeline.common.util.PrepareStage
import pipeline.flow.util.Base
import pipeline.stage.util.CollectionStage
import pipeline.stage.util.StageData

class CustomActionFlow extends Base {
    ArrayList<StageData> stageList

    CustomActionFlow(Config config, ArrayList<StageData> stageList) {
        super(config)
        this.stageList = stageList
    }

    void stageRun() {
        for (StageTuple stageTuple in stageTupleList) {
            try {
                executeStageRun(stageTuple)
            }
            catch (Exception e) {
                exceptionHandler(e, stageTuple.stageName)
            }
        }
    }

    protected void prepareStageList() {
        stageListAddToStageTupleList(EStagePathType.collection, stageList)
    }

    private void executeStageRun(StageTuple stageTuple) {
        CollectionStage customAction = getCustomAction(stageTuple)
        StageData stageData = stageTuple.stageData
        prepareStageData(stageData)
        runCustomAction(config, customAction, stageData)
    }

    private static CollectionStage getCustomAction(StageTuple stageTuple) {
        def customAction = PrepareStage.createStage(stageTuple) as CollectionStage
        EchoStep("ready to execute `$stageTuple.stageName`.")
        return customAction
    }

    private static void runCustomAction(
            Config config,
            CollectionStage customAction,
            StageData stageData
    ) {
        if (stageData.TimeoutMinutes > 0) {
            timeout(time: stageData.TimeoutMinutes, unit: 'MINUTES') {
                customAction.run(config, stageData)
            }
            return
        }
        customAction.run(config, stageData)
    }

    private static void prepareStageData(StageData stageData) {
        stageData.execInit()
        EchoStep("stageData = $stageData.StageArgs")
    }

    private static void exceptionHandler(Exception e, String stageName) {
        String errorMessage = "CollectionStage: `$stageName` execute failed."
        EchoStep("${errorMessage}\n$e")
        throw new Error(errorMessage)
    }
}
