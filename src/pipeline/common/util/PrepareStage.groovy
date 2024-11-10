package pipeline.common.util

import pipeline.common.constants.EStagePathType
import pipeline.stage.util.Base as StageBase

import java.lang.reflect.Constructor

/**
 * Use the Dynamic Class Loader to load the class.
 */
class PrepareStage {
    private static final String PackageStagePath = 'pipeline.stage'
    private static final LinkedHashMap<EStagePathType, String> StagePathMap = [:]

    static {
        StagePathMap.put(EStagePathType.flow, "${PackageStagePath}.flow")
        StagePathMap.put(EStagePathType.collection, "${PackageStagePath}.collection")
    }

    static StageBase createStage(StageTuple stageTuple) {
        Class stageClass = getStageClass(stageTuple)
        Constructor stageConstructor = stageClass.getDeclaredConstructor()
        return stageConstructor.newInstance() as StageBase
    }

    private static Class getStageClass(StageTuple stageTuple) {
        String className = getClassName(stageTuple)
        return StageBase.class.getClassLoader().loadClass(className)
    }

    private static String getClassName(StageTuple stageTuple) {
        def pathType = EStagePathType[stageTuple.stagePathType] as EStagePathType
        String path = StagePathMap[pathType]
        return "$path.$stageTuple.stageName"
    }
}
