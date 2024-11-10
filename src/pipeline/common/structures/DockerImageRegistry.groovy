package pipeline.common.structures

import pipeline.artifact.docker.DockerImageNameGenerator
import pipeline.common.util.BaseStructure

@SuppressWarnings('unused')
class DockerImageRegistry extends BaseStructure {
    private static final String PROJECT_CODE = 'PROJECT_CODE'
    private static final String REGISTRY_URL = 'REGISTRY_URL'
    private static final String REPOSITORY_NAME = 'REPOSITORY_NAME'

    DockerImageRegistry(LinkedHashMap config) {
        super(config)
    }

    protected void structureInitProcess() {
        setConfigProperty('DOCKER_IMAGE_NAME_MAKER', nameGenerator)
    }

    private DockerImageNameGenerator getNameGenerator() {
        String registryURL = (config.containsKey(REGISTRY_URL)) ?
                config[REGISTRY_URL] as String : registryURL
        String repositoryName = (config.containsKey(REPOSITORY_NAME)) ?
                config[REPOSITORY_NAME] as String : repositoryName
        String projectCode = config[PROJECT_CODE] as String
        if (!registryURL || !repositoryName || !projectCode) {
            EchoStep('Warning: One or more required parameters (REGISTRY_URL, ' +
                    'REPOSITORY_NAME, PROJECT_CODE) are null or empty. ' +
                    'The docker image name generator program will not be initialized.')
            return null
        }
        try {
            return new DockerImageNameGenerator(registryURL, repositoryName, projectCode)
        } catch (Exception e) {
            EchoStep("Failed to initialize the Docker image name generator: ${e.message}")
            return null
        }
    }

    private static String getRegistryURL() {
        return getEnvProperty(REGISTRY_URL) as String
    }

    private static String getRepositoryName() {
        return getEnvProperty(REPOSITORY_NAME) as String
    }
}
