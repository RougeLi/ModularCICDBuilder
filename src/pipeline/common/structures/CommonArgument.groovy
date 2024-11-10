package pipeline.common.structures

import pipeline.artifact.util.DoBuildHandler
import pipeline.common.util.BaseStructure

@SuppressWarnings('unused')
class CommonArgument extends BaseStructure {
    private static final String DO_BUILD = 'DO_BUILD'
    private static final String OPEN_BUILD_UI = 'OPEN_BUILD_UI'

    CommonArgument(LinkedHashMap config) {
        super(config)
    }

    protected void structureInitProcess() {
        initProperty('CRON_EXPRESSION', 'H H(0-6) * * *')
        boolean doBuild = (config.containsKey(DO_BUILD)) ?
                config[DO_BUILD] as boolean : false
        boolean openBuildUI = (config.containsKey(OPEN_BUILD_UI)) ?
                config[OPEN_BUILD_UI] as boolean : false
        setConfigProperty('DO_BUILD_HANDLER', new DoBuildHandler(doBuild, openBuildUI))
    }
}
