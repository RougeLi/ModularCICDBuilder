package pipeline.artifact.build.platform

import pipeline.artifact.build.BuildBase
import pipeline.artifact.docker.panels.SetImageTag
import pipeline.common.util.Config
import pipeline.flow.actions.Docker
import pipeline.flow.util.ActionFlow

@SuppressWarnings('unused')
class docker extends BuildBase {
    ActionFlow buildFlow = new Docker(config)

    docker(Config config) {
        super(config)
    }

    void platformArgInit() {
        config.BUILD_LABEL = 'docker_build'
        config.PROJECT_LABEL = 'linux'
        settingUIParamProcess()
    }

    private void settingUIParamProcess() {
        ArrayList<String> dockerImages = []
        config.BUILD_ARGUMENT_LIST.each { Map buildArgumentList ->
            if (!buildArgumentList.containsKey('Image')) {
                return
            }
            String image = buildArgumentList.Image as String
            dockerImages << image
        }
        new SetImageTag(config, dockerImages).initUIParam()
    }
}
