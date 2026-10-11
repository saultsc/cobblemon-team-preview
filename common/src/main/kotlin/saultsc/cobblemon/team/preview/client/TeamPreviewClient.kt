package saultsc.cobblemon.team.preview.client

import net.minecraft.client.Minecraft
import saultsc.cobblemon.team.preview.client.gui.battle.BattlePreviewScreen
import saultsc.cobblemon.team.preview.network.packets.s2c.BattlePreviewPacket
import saultsc.cobblemon.team.preview.network.packets.s2c.BattleTimerUpdatePacket

/**
 * Client side packet handlers. Only ever loaded on the physical client.
 */
object TeamPreviewClient {
    fun handleBattlePreview(packet: BattlePreviewPacket) {
        Minecraft.getInstance().setScreen(
            BattlePreviewScreen(
                battleId = packet.battleId,
                format = packet.format,
                opponentTeam = packet.opponentTeam,
                opponentName = packet.opponentName,
                playerTeam = packet.playerTeam,
                playerName = packet.playerName
            )
        )
    }

    fun handleTimerUpdate(packet: BattleTimerUpdatePacket) {
        val screen = Minecraft.getInstance().screen as? BattlePreviewScreen ?: return
        screen.updateTimer(packet)
    }
}
