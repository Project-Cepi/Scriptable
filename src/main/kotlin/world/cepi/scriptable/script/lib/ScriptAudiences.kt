package world.cepi.scriptable.script.lib

import net.minestom.server.adventure.audience.Audiences
import world.cepi.scriptable.script.access.ScriptableExport

object ScriptAudiences {

    @ScriptableExport
    fun all() = object : ScriptAudience {
        override val audience = Audiences.all()
    }

    @ScriptableExport
    fun players() = object : ScriptAudience {
        override val audience = Audiences.players()
    }

    @ScriptableExport
    fun console() = object : ScriptAudience {
        override val audience = Audiences.console()
    }
}