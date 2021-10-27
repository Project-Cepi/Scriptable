package world.cepi.luae.script.lib

import net.kyori.adventure.audience.Audience
import net.minestom.server.adventure.audience.Audiences
import world.cepi.luae.script.access.ScriptableExport

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