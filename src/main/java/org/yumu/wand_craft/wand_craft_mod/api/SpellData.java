// org/yumu/wand_craft/wand_craft_mod/api/SpellData.java
package org.yumu.wand_craft.wand_craft_mod.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import org.yumu.wand_craft.wand_craft_mod.registries.SpellRegistry;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;

import java.util.Objects;

public class SpellData {

    public static final String SPELL_ID="spellId";

    public static final Codec<SpellData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf(SPELL_ID).forGetter(data->SpellRegistry.getSpellId(data.spell.getSpellName()) )
            ).apply(instance, SpellData::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellData> STREAM_CODEC =
            StreamCodec.composite(
                    ResourceLocation.STREAM_CODEC,
                    spellData -> spellData.spell != null ? SpellRegistry.getSpellId(spellData.spell.getSpellName())  : null,
                    SpellData::new
            );
    private final AbstractSpell spell;

    public SpellData() {
        this.spell = null;
    }
    public SpellData(AbstractSpell spell) {
        this.spell = spell;
    }
    public SpellData(ResourceLocation spellId) {
        this.spell = SpellRegistry.getSpell(spellId);
    }
    public SpellData(String spellId) {
        this.spell = SpellRegistry.getSpell(spellId);
    }

    public AbstractSpell getSpell() {
        return spell;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SpellData spellData = (SpellData) obj;
        return Objects.equals(spell, spellData.spell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spell);
    }
}
