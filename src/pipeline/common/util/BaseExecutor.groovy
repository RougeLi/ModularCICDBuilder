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

    abstract void setupJobConfigureProcess()

    abstract void catchException(Throwable e)

    void limitedTimeExecution(Closure body) {
        timeoutHandler.setTimeout()
        timeoutHandler.call(body)
    }

    void confirmJobConfiguration() {
        stage(stageName) {
            initializePlatformSettings()
            setupJobConfigureProcess()
            syncConfigFieldsToMap()
        }
    }

    static void onEnd() {
        for (Closure closure : endClosures) {
            closure()
        }
    }

    protected void initializePlatformSettings() {
        configureNodeType()
        configureWorkflowType()
    }

    protected void syncConfigFieldsToMap() {
        config.fillingConfigFieldsToMapValue()
        EchoStep(config.getConstConfig())
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

    protected void settingConfigure() {
        if (config.CONFIGURE_PARAMETERS.size() > 0) {
            config.CONFIGURE_PROPERTIES << parameters(config.CONFIGURE_PARAMETERS)
        }
        if (config.CONFIGURE_PROPERTIES.size() == 0) {
            EchoStep('The CONFIGURE_PROPERTIES count is 0; skipping the application of job configurations.')
            return
        }
        //The properties method is used to apply these configurations to the current Jenkins job, making them persistent.
        //noinspection GroovyAssignabilityCheck
        properties(config.CONFIGURE_PROPERTIES)
    }

    protected void configureNodeType() {
        config.BUILD_NODE = JenkinsNodes.CONTROL
        config.DEPLOY_NODE = JenkinsNodes.CONTROL
    }

    protected void configureWorkflowType() {
        switch (config.JOB_PURPOSE) {
            case 'Build':
                config.WORK_FLOW = WorkflowType.Build
                config.PARAM_BUILD_MODE = true
                break
            case 'Deploy':
                config.WORK_FLOW = WorkflowType.Deploy
                config.PARAM_BUILD_MODE = true
                break
            case 'Webhook':
                config.WORK_FLOW = WorkflowType.Dev
                config.PARAM_BUILD_MODE = false
                break
            default:
                config.WORK_FLOW = WorkflowType.Dev
                config.PARAM_BUILD_MODE = true
                break
        }
    }
}
