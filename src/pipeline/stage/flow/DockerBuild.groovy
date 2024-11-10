package pipeline.stage.flow

import pipeline.common.util.Config
import pipeline.stage.util.FlowStage
import pipeline.artifact.docker.ImageBuildWorkflowManager

@SuppressWarnings('unused')
class DockerBuild extends FlowStage {
    String stageDescribe = 'Docker Build'

    void main(Config config) {
        timestamps {
            ImageBuildWorkflowManager.buildAll()
        }
    }
}
