package pipeline.artifact.docker

import pipeline.Pipeline
import com.cloudbees.groovy.cps.NonCPS

class DockerImageNameGenerator extends Pipeline {
    protected String REGISTRY_URL
    protected String REPOSITORY_NAME
    protected String PROJECT_CODE

    DockerImageNameGenerator(
            String registryURL,
            String repositoryName,
            String projectCode
    ) {
        this.REGISTRY_URL = registryURL
        this.REPOSITORY_NAME = repositoryName
        this.PROJECT_CODE = projectCode
        validate()
    }

    @NonCPS
    void validate() {
        assert REGISTRY_URL != null
        assert REPOSITORY_NAME != null
        assert PROJECT_CODE != null
    }

    String getDockerImage(String image) {
        return new StringBuilder()
                .append(GCPArtifactRegistryDockerURL)
                .append(getImageBaseName(image))
                .toString()
    }

    protected String getGCPArtifactRegistryDockerURL() {
        return new StringBuilder()
                .append(REGISTRY_URL)
                .append('/')
                .append(REPOSITORY_NAME)
                .append('/')
                .toString()
    }

    protected String getImageBaseName(String image) {
        return new StringBuilder()
                .append(PROJECT_CODE.toLowerCase())
                .append('/')
                .append(branchName.toLowerCase())
                .append('/')
                .append(image.toLowerCase())
                .toString()
    }

    protected static String getBranchName() {
        return JOB_BASE_NAME
    }
}
