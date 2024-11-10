package pipeline.flow.actions

import pipeline.common.util.Config
import pipeline.flow.util.ActionFlow

class Docker extends ActionFlow {

    Docker(Config config) {
        super(config)
        stageList = []
        stageList << 'SCMCheckOut'
        stageList << 'DockerPreBuild'
        stageList << 'DockerBuild'
        stageList << 'DockerImagePush'
    }
}
