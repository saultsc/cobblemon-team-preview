package saultsc.cobblemon.team.preview

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import saultsc.cobblemon.team.preview.battle.BattlePreviewManager
import saultsc.cobblemon.team.preview.network.NetworkManager

object TeamPreview {
    const val MOD_ID = TeamPreviewBuildDetails.MOD_ID
    const val VERSION = TeamPreviewBuildDetails.VERSION

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    lateinit var networkManager: NetworkManager
        private set

    fun init(networkManager: NetworkManager) {
        LOGGER.info("Launching $MOD_ID $VERSION")
        this.networkManager = networkManager
        BattlePreviewManager.register()
    }

    fun id(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(MOD_ID, path)
}
