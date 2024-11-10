package pipeline.stage.collection

import pipeline.artifact.cd.IServiceLaunch
import pipeline.common.util.Config
import pipeline.stage.util.CollectionStage
import pipeline.stage.util.StageData

@SuppressWarnings('unused')
class ServiceLaunch extends CollectionStage {

    void main(Config config, StageData stageData) {
        stage(stageData.Desc) {
            IServiceLaunch serviceLaunch
            serviceLaunch = getServiceLaunch(stageData.StageArgs)
            if (serviceLaunch == null) {
                throw new Exception('IServiceLaunch is null.')
            }
            node(config.DEPLOY_NODE) {
                transferConfiguration(serviceLaunch)
                prepareServiceLaunch(serviceLaunch)
                startService(serviceLaunch)
            }
        }
    }

    private static IServiceLaunch getServiceLaunch(
            LinkedHashMap stageArgs
    ) {
        return stageArgs['IServiceLaunch'] as IServiceLaunch
    }

    static void transferConfiguration(IServiceLaunch serviceLaunch) {
        EchoStep('Transfer configuration')
        serviceLaunch.transferConfiguration()
    }

    static void prepareServiceLaunch(IServiceLaunch serviceLaunch) {
        EchoStep('Prepare service launch')
        serviceLaunch.prepareServiceLaunch()
    }

    static void startService(IServiceLaunch serviceLaunch) {
        EchoStep('Start service')
        serviceLaunch.startService()
    }
}
