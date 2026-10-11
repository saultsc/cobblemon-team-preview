package saultsc.cobblemon.team.preview.fabric

import net.fabricmc.api.ModInitializer
import saultsc.cobblemon.team.preview.TeamPreview
import saultsc.cobblemon.team.preview.fabric.network.FabricNetworkManager

object TeamPreviewFabric : ModInitializer {
    override fun onInitialize() {
        TeamPreview.init(FabricNetworkManager)
        FabricNetworkManager.registerPayloads()
    }
}
