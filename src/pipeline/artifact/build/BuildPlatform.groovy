package pipeline.artifact.build


import pipeline.Pipeline

/**
 * Use the Dynamic Class Loader to load the class.
 */
class BuildPlatform extends Pipeline {
    private static buildPlatformBasePath = 'pipeline.artifact.build.platform.'

    static Class<BuildBase> getBuildPlatformClass(String buildPlatform) {
        ClassLoader buildPlatformClassLoader = BuildBase.class.getClassLoader()
        try {
            String classPath = "${buildPlatformBasePath}${buildPlatform}"
            return buildPlatformClassLoader.loadClass(classPath) as Class<BuildBase>
        } catch (ClassNotFoundException ignored) {
            return DefaultBuildPlatform
        }
    }
}
