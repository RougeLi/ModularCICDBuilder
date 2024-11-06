package pipeline.stage.customactions

import net.sf.json.groovy.JsonSlurper
import org.jenkinsci.plugins.gwt.GenericCause
import pipeline.common.util.Config
import pipeline.stage.util.CustomAction
import pipeline.stage.util.StageData

@SuppressWarnings('unused')
class TestStage extends CustomAction {
    void main(Config config, StageData stageData) {
        stage(stageData.Desc) {
            def genericCause = currentBuild.rawBuild.getCause(GenericCause)
            def vars = genericCause.getResolvedVariables()
            EchoStep(vars.ref)
            EchoStep(vars.head_commit)
            def headCommit = [:]
            headCommit << new JsonSlurper().parseText(vars.head_commit)
            EchoStep(headCommit.modified as String)
        }
    }
}
