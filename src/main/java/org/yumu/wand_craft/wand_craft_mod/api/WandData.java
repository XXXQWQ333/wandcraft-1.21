package org.yumu.wand_craft.wand_craft_mod.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.yumu.wand_craft.wand_craft_mod.registries.SpellRegistry;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 法杖数据类，用于存储和管理法杖的属性信息，包括最大法术槽位数和已装载的法术列表。
 */
public class WandData {
    public static final String MAX_SPELL_SLOT = "maxSpellSlot";
    public static final String SPELLS = "spells";
    public static final String CASTCOUNT = "castCount";
    public static final String COOLDOWN_TIME = "coolDownTime";
    public static final String CONTROLLABLE = "controllable";
    public static final String MANA_REGEN = "manaRegen";

    public static final String INDEX = "Index";

    //法杖属性
    ///法杖的槽位
    int maxSpellSlot = 0;
    ///存储的法术
    List<ResourceLocation> spellIds;
    /// 法杖提供的施法次数，未初始化时为0
    int castCount = 0;
    /// 法杖的装填需要的（tick）冷却时间
    int coolDownTime = 0;
    /// 法杖的可控性
    Boolean isControllable=false;
    /// 法杖提供的回蓝
    int manaRegen = 0;

    //隐藏属性：暂时无


    //法术链索引
    int Index = 0;





    /**
     * 用于序列化与反序列化WandData对象的Codec编解码器。
     * 支持将WandData对象编码为Mojang序列化格式或从该格式解码。
     */
    public static final Codec<WandData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf(MAX_SPELL_SLOT).forGetter(WandData::getMaxSpellSlot),
                    ResourceLocation.CODEC.listOf().fieldOf(SPELLS).forGetter(WandData::getSpellIds),
                    Codec.INT.fieldOf(CASTCOUNT).forGetter(WandData::getCastCount),
                    Codec.INT.fieldOf(COOLDOWN_TIME).forGetter(WandData::getCoolDownTime),
                    Codec.BOOL.fieldOf(CONTROLLABLE).forGetter(WandData::getControllable),
                    Codec.INT.fieldOf(MANA_REGEN).forGetter(WandData::getManaRegen),
                    Codec.INT.fieldOf(INDEX).forGetter(WandData::getIndex)
            ).apply(instance, WandData::new)
    );

    /**
     * 用于网络传输时对WandData对象进行编码和解码的StreamCodec流编解码器。
     * 可在客户端和服务端之间同步WandData对象的数据。
     */
    public static final StreamCodec<RegistryFriendlyByteBuf, WandData> STREAM_CODEC = StreamCodec.of(
            (buf, wandData) -> {
                buf.writeInt(wandData.getMaxSpellSlot());
                buf.writeInt(wandData.getSpellIds().size());
                buf.writeInt(wandData.getCastCount());
                buf.writeInt(wandData.getCoolDownTime());
                buf.writeBoolean(wandData.getControllable());
                buf.writeInt(wandData.getManaRegen());
                buf.writeInt(wandData.getIndex());
                for (ResourceLocation spellId : wandData.getSpellIds()) {
                    buf.writeResourceLocation(spellId);
                }
            },
            buf -> {
                int maxSpellSlot = buf.readInt();
                int spellCount = buf.readInt();
                int castCount = buf.readInt();
                int coolDownTime = buf.readInt();
                Boolean isControllable = buf.readBoolean();
                int manaRegen = buf.readInt();
                int Index = buf.readInt();
                List<ResourceLocation> spellIds = new ArrayList<>();
                for (int i = 0; i < spellCount; i++) {
                    spellIds.add(buf.readResourceLocation());
                }
                return new WandData(maxSpellSlot, spellIds, castCount,coolDownTime,isControllable,manaRegen,Index);
            }
    );

    /**
     * 判断两个WandData对象是否相等
     * @param obj 要比较的对象
     * @return 如果两个对象内容相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WandData wandData = (WandData) obj;
        return maxSpellSlot == wandData.maxSpellSlot &&
                spellIds.equals(wandData.spellIds) &&
                Index == wandData.Index;
    }

    /**
     * 计算当前对象的哈希值
     * @return 基于maxSpellSlot、spellIds和Index计算出的哈希码
     */
    @Override
    public int hashCode() {
        int result = Integer.hashCode(maxSpellSlot);
        result = 31 * result + spellIds.hashCode();
        result = 31 * result + Integer.hashCode(Index);
        return result;
    }

    /**
     * 构造一个完整的WandData对象
     * @param maxSpellSlot 最大法术槽数量
     * @param spellIds 法术ID列表，可以为null
     * @param castCount 法术释放次数
     * @param Index 当前选中的法术索引
     */
    public WandData(int maxSpellSlot, List<ResourceLocation> spellIds, int castCount,int coolDownTime,Boolean isControllable,int manaRegen ,int Index) {
        this.maxSpellSlot = maxSpellSlot;
        this.spellIds = spellIds != null ? spellIds : new ArrayList<>();
        this.castCount = castCount;
        this.coolDownTime = coolDownTime;
        this.isControllable = isControllable;
        this.manaRegen = manaRegen;
        this.Index = Index;
    }


    /**
     * 构造一个默认的WandData对象，所有字段初始化为默认值
     */
    public WandData() {
        this(0, null,0,0,false, 0, 0);
    }

    /**
     * 获取当前法杖的最大法术槽数量
     * @return 当前最大法术槽数量
     */
    public int getMaxSpellSlot() {
        return maxSpellSlot;
    }

    /**
     * 获取当前法杖提供的法术释放次数
     * @return 当前法术释放次数
     */
    public int getCastCount() {
        return castCount;
    }

    /**
     * 获取当前法杖装载的法术ID列表副本
     * @return 包含所有法术ID的新ArrayList实例
     */
    public List<ResourceLocation> getSpellIds() {
        return new ArrayList<>(spellIds);
    }

    /**
     * 获取当前法杖装载的法术列表（从注册表获取）
     * @param registry 法术注册表
     * @return 包含所有法术的新ArrayList实例
     */
    public List<AbstractSpell> getSpells(Registry<AbstractSpell> registry) {
        List<AbstractSpell> spells = new ArrayList<>();
        for (ResourceLocation id : spellIds) {
            registry.getOptional(id).ifPresent(spells::add);
        }
        return spells;
    }

    public Boolean getControllable() {
        return isControllable;
    }

    public int getCoolDownTime() {
        return coolDownTime;
    }

    public int getManaRegen() {
        return manaRegen;
    }

    /**
     * 随机重新设置法杖的最大法术槽数量（范围为1~9）以及法术释放次数（范围为1~2）
     * @return 总是返回true
     */
    public boolean reforge(){
        Random random = new Random();
        this.maxSpellSlot = 1 + random.nextInt(9);
        this.castCount = 1;
        this.manaRegen= random.nextInt(5);
        return true;
    }

    /**
     * 设置法杖的最大法术槽数量
     * @param maxSpellSlot 新的最大法术槽数量
     */
    public void setMaxSpellSlot(int maxSpellSlot) {
        this.maxSpellSlot = maxSpellSlot;
    }

    /**
     * 设置法杖的法术释放次数
     * @param castcount 新的法术释放次数
     */
    public void setCastcount(int castcount) {
        this.castCount = castcount;
    }

    /**
     * 设置法杖的法术ID列表
     * @param spellIds 新的法术ID列表，可以为null
     */
    public void setSpellIds(List<ResourceLocation> spellIds) {
        this.spellIds = spellIds != null ? spellIds : new ArrayList<>();
    }

    /**
     * 向法杖中添加一个新的法术ID
     * @param spellId 要添加的法术ID
     */
    public void addSpellId(ResourceLocation spellId) {
        spellIds.add(spellId);
    }

    /**
     * 向法杖中添加一个新的法术
     * @param spell 要添加的法术对象
     */
    public void addSpell(AbstractSpell spell, Registry<AbstractSpell> registry) {
        ResourceLocation id = registry.getKey(spell);
        if (id != null) {
            spellIds.add(id);
        }
    }

    /**
     * 根据索引获取法杖中的某个法术
     * @param index 法术在列表中的索引位置
     * @return 指定索引处的法术对象
     */
    public AbstractSpell getSpell(int index) {
        if (index < 0 || index >= spellIds.size()) {
            return null;
        }
        return SpellRegistry.REGISTRY.get(spellIds.get(index));
    }

    /**
     * 检查法杖是否已初始化
     * @return 如果法杖已初始化返回true，否则返回false
     */
    public boolean isInitialized() {
        return maxSpellSlot > 0;
    }

    /**
     * 获取当前选中的法术索引
     * @return 当前选中的法术索引
     */
    public int getIndex() {
        return Index;
    }

    /**
     * 设置当前选中的法术索引
     * @param Index 新的选中法术索引
     */
    public void setIndex(int Index) {
        this.Index = Index%maxSpellSlot;
    }

    public ResourceLocation selectNextSpell(){
        if (spellIds.isEmpty()|| maxSpellSlot > spellIds.size()) {
            return null;
        }
        ResourceLocation currentSpell = spellIds.get(Index);
        Index = (Index + 1) % maxSpellSlot;
        return currentSpell;
    }

}