package pipeline.stage.flow

import pipeline.common.util.Config
import pipeline.stage.util.FlowStage
import pipeline.artifact.docker.ImageBuildWorkflowManager

@SuppressWarnings('unused')
class DockerImagePush extends FlowStage {
    String stageDescribe = 'Docker Image Push'

    void main(Config config) {
        ImageBuildWorkflowManager.pushAll()
    }
}
