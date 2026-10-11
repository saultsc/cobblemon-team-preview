package saultsc.cobblemon.team.preview.fabric.client

import net.fabricmc.api.ClientModInitializer
import saultsc.cobblemon.team.preview.fabric.network.FabricNetworkManager

object TeamPreviewFabricClient : ClientModInitializer {
    override fun onInitializeClient() {
        FabricNetworkManager.registerClientHandlers()
    }
}
