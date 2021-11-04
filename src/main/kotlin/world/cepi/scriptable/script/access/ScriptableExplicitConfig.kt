package world.cepi.scriptable.script.access

import org.graalvm.polyglot.HostAccess

object ScriptableExplicitConfig {

    val explicitMode = HostAccess.newBuilder()
        .allowAccessAnnotatedBy(ScriptableExport::class.java)
        .allowImplementationsAnnotatedBy(ScriptableImplementation::class.java)
        .allowImplementationsAnnotatedBy(FunctionalInterface::class.java)
        .methodScoping(true)
        .build()


}