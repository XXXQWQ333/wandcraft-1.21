package org.yumu.wand_craft.wand_craft_mod.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import org.jetbrains.annotations.Nullable;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;

/**
 * PlayerMagicProvider 是一个用于序列化和反序列化玩家魔法数据的附件提供者。
 * 它实现了 NeoForge 的 IAttachmentSerializer 接口，用于将 MagicData 对象与 NBT 数据进行转换。
 */
public class PlayerMagicProvider implements IAttachmentSerializer<CompoundTag, MagicData> {
    /**
     * 从给定的附件持有者和 NBT 标签中读取并构建 MagicData 实例。

     * @param provider 用于查找数据的 HolderLookup.Provider。
     * @return 构建好的 MagicData 实例。
     */
    @Override
    public MagicData read(IAttachmentHolder iAttachmentHolder, CompoundTag compoundTag, HolderLookup.Provider provider) {
        var magicData = iAttachmentHolder instanceof ServerPlayer serverPlayer ? new MagicData(serverPlayer) : new MagicData();
        magicData.loadNBTData(compoundTag, provider);
        return magicData;
    }

    @Override
    public @Nullable CompoundTag write(MagicData magicData, HolderLookup.Provider provider) {
        var tag=new CompoundTag();
        magicData.saveNBTData(tag, provider);
        return tag;
    }
}
