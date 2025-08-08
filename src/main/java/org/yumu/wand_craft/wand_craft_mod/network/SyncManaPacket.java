package org.yumu.wand_craft.wand_craft_mod.network;

import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;

public class SyncManaPacket implements CustomPacketPayload {
    private int playerMana=0;
    public static final CustomPacketPayload.Type<SyncManaPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WandCraft.MODID, "sync_mana"));


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }

    @Override
    public ClientboundCustomPayloadPacket toVanillaClientbound() {
        return CustomPacketPayload.super.toVanillaClientbound();
    }

    @Override
    public ServerboundCustomPayloadPacket toVanillaServerbound() {
        return CustomPacketPayload.super.toVanillaServerbound();
    }

}
