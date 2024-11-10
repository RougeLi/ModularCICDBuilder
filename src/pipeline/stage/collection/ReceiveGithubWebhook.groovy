package pipeline.stage.collection

import net.sf.json.groovy.JsonSlurper
import org.jenkinsci.plugins.gwt.GenericCause
import pipeline.common.util.Config
import pipeline.stage.util.CollectionStage
import pipeline.stage.util.StageData

@SuppressWarnings('unused')
class ReceiveGithubWebhook extends CollectionStage {
    void main(Config config, StageData stageData) {
        stage(stageData.Desc) {
            def genericCause = currentBuild.rawBuild.getCause(GenericCause)
            def postContentMap = [:]
            postContentMap << new JsonSlurper().parseText(genericCause.postContent)
            def ref = postContentMap.ref as String
            EchoStep(getBranch(ref))
            def pusher = postContentMap.pusher as Map
            EchoStep(pusher.toString())
            EchoStep(pusher.name as String)
        }
    }

    static def getBranch(String ref) {
        def parts = ref.split('/')
        return parts[-1]
    }
}
