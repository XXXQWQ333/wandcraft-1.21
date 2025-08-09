package org.yumu.wand_craft.wand_craft_mod.item.spell;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class Spell {

    protected String spellName;
    public Spell(String spellName) {
        this.spellName = spellName;
    }
    public String getSpellName() {
        return spellName;
    }

    // 添加编解码器
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.STRING.fieldOf("name").forGetter(Spell::getSpellName)
            ).apply(builder, Spell::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Spell> STREAM_CODEC = StreamCodec.of(
            (buf, spell) -> buf.writeUtf(spell.getSpellName()),
            buf -> new Spell(buf.readUtf())
    );
}
