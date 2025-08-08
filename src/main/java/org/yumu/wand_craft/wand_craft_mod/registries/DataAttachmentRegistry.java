package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;
import org.yumu.wand_craft.wand_craft_mod.capability.PlayerMagicProvider;

/**
 * 数据附件注册类，用于注册和管理游戏中的数据附件类型
 */
public class DataAttachmentRegistry {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, WandCraft.MODID);
    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<MagicData>> MAGIC_DATA = ATTACHMENT_TYPES.register("magic_data",
            () -> AttachmentType.builder((holder) -> holder instanceof ServerPlayer serverPlayer ? new MagicData(serverPlayer) : new MagicData()).serialize(new PlayerMagicProvider()).build());

}
