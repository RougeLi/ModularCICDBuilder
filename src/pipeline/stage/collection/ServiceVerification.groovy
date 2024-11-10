package pipeline.stage.collection

import pipeline.artifact.cd.IServiceVerification
import pipeline.common.util.Config
import pipeline.stage.util.CollectionStage
import pipeline.stage.util.StageData

@SuppressWarnings('unused')
class ServiceVerification extends CollectionStage {

    void main(Config config, StageData stageData) {
        stage(stageData.Desc) {
            IServiceVerification serviceVerification
            serviceVerification = getServiceVerification(stageData.StageArgs)
            if (serviceVerification == null) {
                throw new Exception('IServiceVerification is null.')
            }
            node(config.DEPLOY_NODE) {
                serviceTesting(serviceVerification)
            }
        }
    }

    private static IServiceVerification getServiceVerification(
            LinkedHashMap stageArgs
    ) {
        return stageArgs['IServiceVerification'] as IServiceVerification
    }

    private static void serviceTesting(
            IServiceVerification serviceVerification
    ) {
        EchoStep('Service testing')
        serviceVerification.serviceTesting()
    }
}
