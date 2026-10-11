package saultsc.cobblemon.team.preview.neoforge

import net.neoforged.fml.ModLoadingContext
import net.neoforged.fml.common.Mod
import saultsc.cobblemon.team.preview.TeamPreview
import saultsc.cobblemon.team.preview.neoforge.network.NeoForgeNetworkManager

@Mod(TeamPreview.MOD_ID)
object TeamPreviewNeoForge {
    init {
        TeamPreview.init(NeoForgeNetworkManager)

        val modBus = requireNotNull(ModLoadingContext.get().activeContainer.eventBus) { "Missing mod event bus" }
        modBus.addListener(NeoForgeNetworkManager::registerPayloads)
    }
}
