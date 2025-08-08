// org/yumu/wand_craft/wand_craft_mod/api/MagicData.java
package org.yumu.wand_craft.wand_craft_mod.api;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.yumu.wand_craft.wand_craft_mod.network.SyncManaPacket;
import org.yumu.wand_craft.wand_craft_mod.registries.DataAttachmentRegistry;

public class MagicData {

    public MagicData() {
    }

    public MagicData(ServerPlayer serverPlayer) {
        this.serverPlayer = serverPlayer;
    }

    private ServerPlayer serverPlayer = null;
    private float mana;
    public static final String MANA = "mana";

    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
        syncToClient();
    }

    public void addMana(float mana) {
        setMana(this.mana + mana);
    }

    public static MagicData getPlayerMagicData(LivingEntity livingEntity){
        return livingEntity.getData(DataAttachmentRegistry.MAGIC_DATA);
    }

    /**
     * 将 MagicData 数据保存到 NBT 标签中。
     *
     * @param compound NBT 标签
     * @param provider 数据提供器
     */
    public void saveNBTData(CompoundTag compound, HolderLookup.Provider provider) {
        compound.putFloat(MANA, mana);
    }

    /**
     * 从 NBT 标签中加载 MagicData 数据。
     *
     * @param compound NBT 标签
     * @param provider 数据提供器
     */
    public void loadNBTData(CompoundTag compound, HolderLookup.Provider provider) {
        mana = compound.getFloat(MANA);
    }

    /**
     * 同步 mana 值到客户端
     */
    private void syncToClient() {
        // 只在服务端玩家存在时同步
        if (serverPlayer != null) {
            PacketDistributor.sendToPlayer(serverPlayer, new SyncManaPacket(this));
        }
    }
}
