package pipeline.module.strategy

import pipeline.common.util.Config
import pipeline.common.util.StrategyTuple
import pipeline.module.util.StrategyList
import pipeline.stage.util.StageData

@SuppressWarnings('unused')
class TestStrategy extends StrategyList {
    TestStrategy(Config config, LinkedHashMap<Serializable, Serializable> moduleArgs) {
        super(config, moduleArgs)
    }

    ArrayList<StrategyTuple> getStrategyList() {
        def testStage = new StageData('TestStage', this.moduleArgs)
        testStage.Desc = 'test'
        return strategyTuples << new StrategyTuple([testStage])
    }
}
