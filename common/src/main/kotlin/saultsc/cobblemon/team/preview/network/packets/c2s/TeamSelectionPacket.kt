package saultsc.cobblemon.team.preview.network.packets.c2s

import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import saultsc.cobblemon.team.preview.TeamPreview
import saultsc.cobblemon.team.preview.battle.BattlePreviewManager
import saultsc.cobblemon.team.preview.network.ServerboundPacket
import java.util.UUID

/**
 * The player's current team selection in the preview.
 *
 * Sent on every change so the server can auto complete the selection if the time runs out,
 * and once more with [confirmed] set when the player locks it in.
 *
 * @param selectedIndices indices inside the team the server sent in [saultsc.cobblemon.team.preview.network.packets.s2c.BattlePreviewPacket],
 * in the order they were picked.
 */
data class TeamSelectionPacket(
    val battleId: UUID,
    val selectedIndices: List<Int>,
    val confirmed: Boolean
) : ServerboundPacket {

    override fun type(): CustomPacketPayload.Type<TeamSelectionPacket> = TYPE

    override fun handle(player: ServerPlayer) =
        BattlePreviewManager.handleTeamSelection(battleId, player, selectedIndices, confirmed)

    companion object {
        /** A party never has more Pokémon than this, anything longer is a malformed packet. */
        private const val MAX_INDICES = 6

        val TYPE = CustomPacketPayload.Type<TeamSelectionPacket>(TeamPreview.id("team_selection"))

        val CODEC: StreamCodec<RegistryFriendlyByteBuf, TeamSelectionPacket> = StreamCodec.of(::write, ::read)

        private fun write(buf: RegistryFriendlyByteBuf, packet: TeamSelectionPacket) {
            buf.writeUUID(packet.battleId)
            buf.writeVarInt(packet.selectedIndices.size)
            packet.selectedIndices.forEach(buf::writeVarInt)
            buf.writeBoolean(packet.confirmed)
        }

        private fun read(buf: RegistryFriendlyByteBuf): TeamSelectionPacket {
            val battleId = buf.readUUID()
            val size = buf.readVarInt()
            require(size in 0..MAX_INDICES) { "Invalid team selection size: $size" }
            return TeamSelectionPacket(
                battleId = battleId,
                selectedIndices = List(size) { buf.readVarInt() },
                confirmed = buf.readBoolean()
            )
        }
    }
}
