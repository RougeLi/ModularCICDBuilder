package pipeline.stage.flow

import pipeline.artifact.ansible.AnsibleConsulImageManager
import pipeline.stage.util.FlowStage
import pipeline.common.util.Config

@SuppressWarnings('unused')
class BuildAnsibleConsulImage extends FlowStage {
    public static String DOCKER_IMAGE = 'ansible-consul'
    public static String ANSIBLE_WORK_DIR = '/ansible'
    String stageDescribe = 'Build Consul Image'

    void main(Config config) {
        def manager = new AnsibleConsulImageManager(DOCKER_IMAGE, ANSIBLE_WORK_DIR)
        if (manager.isImageExist) {
            return
        }
        EchoStep("$DOCKER_IMAGE not found. Building Dockerfile...")
        manager.buildDockerfile()
    }
}
