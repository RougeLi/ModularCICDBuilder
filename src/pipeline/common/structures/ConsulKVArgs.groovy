package pipeline.common.structures

import pipeline.common.util.BaseStructure
import pipeline.common.consul.ConsulKVBaseInfo

@SuppressWarnings('unused')
class ConsulKVArgs extends BaseStructure {
    private static final String CONSUL_HOST = 'CONSUL_HOST'

    ConsulKVArgs(LinkedHashMap config) {
        super(config)
    }

    protected void structureInitProcess() {
        String host = (config.containsKey(CONSUL_HOST)) ?
                config[CONSUL_HOST] as String : consulHost
        if (host == null) {
            EchoStep('Consul host is null, use default value.')
            host = ''
        }
        setConfigProperty('CONSUL_KV', new ConsulKVBaseInfo(host))
    }

    private static String getConsulHost() {
        return getEnvProperty(CONSUL_HOST) as String
    }
}
