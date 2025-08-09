// org/yumu/wand_craft/wand_craft_mod/item/WandData.java
package org.yumu.wand_craft.wand_craft_mod.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.yumu.wand_craft.wand_craft_mod.item.spell.Spell;

import java.util.ArrayList;
import java.util.List;

public class WandData {


    public static final String MAX_SPELL_SLOT = "maxSpellSlot";
    public static final String SPELLS = "spells";

    int maxSpellSlot = 0;
    List<Spell> spells = new ArrayList<>();

    // 修正Spell编解码器引用并添加流编解码器
    public static final Codec<WandData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf(MAX_SPELL_SLOT).forGetter(WandData::getMaxSpellSlot),
            Spell.CODEC.listOf().fieldOf(SPELLS).forGetter(WandData::getSpells)
        ).apply(instance, WandData::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, WandData> STREAM_CODEC = StreamCodec.of(
        (buf, wandData) -> {
            buf.writeInt(wandData.getMaxSpellSlot());
            buf.writeInt(wandData.getSpells().size());
            for (Spell spell : wandData.getSpells()) {
                Spell.STREAM_CODEC.encode(buf, spell);
            }
        },
        buf -> {
            int maxSpellSlot = buf.readInt();
            int spellCount = buf.readInt();
            List<Spell> spells = new ArrayList<>();
            for (int i = 0; i < spellCount; i++) {
                spells.add(Spell.STREAM_CODEC.decode(buf));
            }
            return new WandData(maxSpellSlot, spells);
        }
    );
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
                spells.equals(wandData.spells);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(maxSpellSlot);
        result = 31 * result + spells.hashCode();
        return result;
    }

    public WandData(int maxSpellSlot) {
        this(maxSpellSlot, null);
    }

    public WandData(int maxSpellSlot, List<Spell> spells) {
        this.maxSpellSlot = maxSpellSlot;
        this.spells = spells != null ? spells : new ArrayList<>();
    }

    public WandData() {
        this(0, null);
    }

    public int getMaxSpellSlot() {
        return maxSpellSlot;
    }

    public List<Spell> getSpells() {
        // 返回一个副本
        return new ArrayList<>(spells); // 创建新的ArrayList包含所有元素
    }

    public void setMaxSpellSlot(int maxSpellSlot) {
        this.maxSpellSlot = maxSpellSlot;
    }

    public void setSpells(List<Spell> spells) {
        this.spells = spells != null ? spells : new ArrayList<>();
    }

    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    public Spell getSpell(int index) {
        return spells.get(index);
    }

    /**
     * 检查法杖是否已初始化
     * @return 如果法杖已初始化返回true，否则返回false
     */
    public boolean isInitialized() {
        return maxSpellSlot > 0;
    }
}
