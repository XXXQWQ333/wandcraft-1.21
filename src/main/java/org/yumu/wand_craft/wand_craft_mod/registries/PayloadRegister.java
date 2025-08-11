// 文件: org/yumu/wand_craft/wand_craft_mod/registries/PayloadRegister.java
package org.yumu.wand_craft.wand_craft_mod.registries;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.network.SyncManaPacket;


@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = WandCraft.MODID)
public class PayloadRegister {

    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar payloadRegistrar = event.registrar(WandCraft.MODID);
        payloadRegistrar.playToClient(SyncManaPacket.TYPE, SyncManaPacket.STREAM_CODEC, SyncManaPacket::handle);
        // 只注册服务端到客户端的单向通信

    }
}
