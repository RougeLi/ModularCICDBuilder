package pipeline.module.ui

import pipeline.common.util.Config
import pipeline.module.util.SetupUI

@SuppressWarnings('unused')
class TestStrategy extends SetupUI {
    TestStrategy(Config config, LinkedHashMap<Serializable, Serializable> moduleArgs) {
        super(config, moduleArgs)
    }

    void setupUI() {
    }
}
