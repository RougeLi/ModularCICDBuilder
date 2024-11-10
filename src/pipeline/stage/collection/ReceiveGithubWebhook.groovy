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
        }
    }
}
