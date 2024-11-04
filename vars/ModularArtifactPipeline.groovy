import pipeline.artifact.util.ArtifactExecutor

def call(Closure body) {
    def config = GenerateModularConfig.getConfig(this, body)
    def artifactExecutor = new ArtifactExecutor(config)
    try {
        artifactExecutor.confirmJobConfiguration()
        artifactExecutor.limitedTimeExecution {
            artifactExecutor.executeBuildFlow()
            artifactExecutor.executeDeployFlow()
        }
    } catch (Throwable error) {
        artifactExecutor.catchException(error)
    } finally {
        artifactExecutor.onEnd()
    }
}
