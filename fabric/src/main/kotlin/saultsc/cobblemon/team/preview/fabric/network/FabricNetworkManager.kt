package saultsc.cobblemon.team.preview.fabric.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import saultsc.cobblemon.team.preview.network.TeamPreviewNetwork
import saultsc.cobblemon.team.preview.network.ClientboundPacket
import saultsc.cobblemon.team.preview.network.NetworkManager
import saultsc.cobblemon.team.preview.network.PacketRegistrar
import saultsc.cobblemon.team.preview.network.ServerboundPacket

object FabricNetworkManager : NetworkManager {
    /** Registers payload types on both sides and the server receivers. */
    fun registerPayloads() = TeamPreviewNetwork.register(object : PacketRegistrar {
        override fun <T : ClientboundPacket> clientbound(type: CustomPacketPayload.Type<T>, codec: StreamCodec<RegistryFriendlyByteBuf, T>) {
            PayloadTypeRegistry.playS2C().register(type, codec)
        }

        override fun <T : ServerboundPacket> serverbound(type: CustomPacketPayload.Type<T>, codec: StreamCodec<RegistryFriendlyByteBuf, T>) {
            PayloadTypeRegistry.playC2S().register(type, codec)
            ServerPlayNetworking.registerGlobalReceiver(type) { packet, context -> packet.handle(context.player()) }
        }
    })

    /** Registers the client receivers. Must only be called on the physical client. */
    fun registerClientHandlers() = TeamPreviewNetwork.register(object : PacketRegistrar {
        override fun <T : ClientboundPacket> clientbound(type: CustomPacketPayload.Type<T>, codec: StreamCodec<RegistryFriendlyByteBuf, T>) {
            ClientPlayNetworking.registerGlobalReceiver(type) { packet, _ -> packet.handle() }
        }

        override fun <T : ServerboundPacket> serverbound(type: CustomPacketPayload.Type<T>, codec: StreamCodec<RegistryFriendlyByteBuf, T>) = Unit
    })

    override fun sendToPlayer(player: ServerPlayer, packet: CustomPacketPayload) = ServerPlayNetworking.send(player, packet)

    override fun sendToServer(packet: CustomPacketPayload) = ClientPlayNetworking.send(packet)
}
