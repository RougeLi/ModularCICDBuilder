package pipeline.common.util

import pipeline.common.constants.JenkinsNodes
import pipeline.Pipeline
import pipeline.common.constants.WorkflowType

abstract class BaseExecutor extends Pipeline {
    static final ArrayList<Closure> endClosures = []
    protected TimeoutHandler timeoutHandler
    protected int limitCount = 5
    protected Config config
    protected String stageName = 'Job Configuration Confirmation'

    BaseExecutor(Config config) {
        this.config = config
        timeoutHandler = new TimeoutHandler(config)
    }

    abstract void configureCustomSettings()

    abstract void catchException(Throwable e)

    void limitedTimeExecution(Closure body) {
        timeoutHandler.setTimeout()
        timeoutHandler.call(body)
    }

    void confirmJobConfiguration() {
        stage(stageName) {
            configureCustomSettings()
            syncConfigFieldsToMap()
        }
    }

    static void onEnd() {
        for (Closure closure : endClosures) {
            closure()
        }
    }

    protected void syncConfigFieldsToMap() {
        config.fillingConfigFieldsToMapValue()
        EchoStep(config.getConstConfig())
    }

    protected void platformLibInit() {
        NodeTypeInit()
        flowTypeInit()
    }

    protected void NodeTypeInit() {
        config.BUILD_NODE = JenkinsNodes.CONTROL
        config.DEPLOY_NODE = JenkinsNodes.CONTROL
    }

    protected void flowTypeInit() {
        switch (config.JOB_PURPOSE) {
            case 'build':
                config.WORK_FLOW = WorkflowType.Build
                break
            case 'deploy':
                config.WORK_FLOW = WorkflowType.Deploy
                break
            default:
                config.WORK_FLOW = WorkflowType.Dev
                break
        }
    }

    protected void echoException(Throwable e) {
        def stackTraceMessageBuilder = new StringBuilder(e.toString())
        ArrayList stackTrace = e.getStackTrace()
        if (stackTrace.size() > 0) {
            stackTraceMessageBuilder.append("\n\nError StackTrace :")
            int limit = (stackTrace.size() > limitCount) ?
                    limitCount :
                    stackTrace.size() - 1
            for (int i = 0; i < limit; i++) {
                stackTraceMessageBuilder.append("\n${stackTrace[i]}")
            }
        }
        stackTraceMessageBuilder.append("\n")
        error(stackTraceMessageBuilder.toString())
    }

    protected void settingProperties() {
        if (config.CUSTOM_PARAMETERS.size() > 0) {
            config.CUSTOM_PROPERTIES.add(parameters(config.CUSTOM_PARAMETERS))
        } else {
            EchoStep('allParameters.size() = 0, skipped.')
            return
        }
        //noinspection GroovyAssignabilityCheck
        properties(config.CUSTOM_PROPERTIES)
    }
}
