package pipeline.stage.flow

import pipeline.common.util.Config
import pipeline.stage.util.FlowStage
import pipeline.artifact.docker.DockerfileTemplateProvider
import pipeline.artifact.docker.DockerfileTemplate

@SuppressWarnings('unused')
class DockerPreBuild extends FlowStage {
    String stageDescribe = 'Prepare Docker Build'

    void main(Config config) {
        def provider = new DockerfileTemplateProvider(config)
        ArrayList<DockerfileTemplate> templates = provider.getTemplates()
        templates.each { DockerfileTemplate dockerfileTemplate ->
            dockerfileTemplate.main()
        }
    }
}
