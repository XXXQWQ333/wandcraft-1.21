// org/yumu/wand_craft/wand_craft_mod/network/SyncManaPacket.java
package org.yumu.wand_craft.wand_craft_mod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;

public class SyncManaPacket implements CustomPacketPayload {
    private float playerMana = 0;
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncManaPacket> STREAM_CODEC = CustomPacketPayload.codec(SyncManaPacket::write, SyncManaPacket::new);
    public static final CustomPacketPayload.Type<SyncManaPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WandCraft.MODID, "sync_mana"));

    public SyncManaPacket(MagicData playerMagicData){
        //服务端
        this.playerMana = playerMagicData.getMana();
    }

    public SyncManaPacket(FriendlyByteBuf buf) {
        playerMana = buf.readFloat();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(playerMana);
    }

    public static void handle(SyncManaPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            MagicData playerMagicData = MagicData.getPlayerMagicData(context.player());
            playerMagicData.setMana(packet.playerMana);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
