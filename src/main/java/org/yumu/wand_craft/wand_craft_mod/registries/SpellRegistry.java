// org/yumu/wand_craft/wand_craft_mod/registries/SpellRegistry.java
package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.NoneSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.effect.SpeedEnhancementSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.projectile.FireballSpell;

import java.util.List;

public class SpellRegistry {
    // 创建法术注册表键
    public static final ResourceKey<Registry<AbstractSpell>> SPELL_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(WandCraft.MODID, "spells"));

    // 创建延迟注册器
    private static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SPELL_REGISTRY_KEY, WandCraft.MODID);

    public static final Registry<AbstractSpell> REGISTRY = new RegistryBuilder<>(SPELL_REGISTRY_KEY).create();

    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }
    public static void registerRegistry(NewRegistryEvent event) {
        event.register(REGISTRY);
    }
    public static AbstractSpell getSpell(ResourceLocation resourceLocation){
        return REGISTRY.get(resourceLocation);
    }

    public static AbstractSpell getSpell(String spellId){
        return getSpell(ResourceLocation.parse(spellId));
    }

    public static List<AbstractSpell> getSpells(){
        return REGISTRY.stream().toList();
    }

    private static <T extends AbstractSpell> DeferredHolder<AbstractSpell, T> registerSpell(T spell){
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }


    public static final DeferredHolder<AbstractSpell, NoneSpell> NONE = registerSpell(new NoneSpell());

    public static final DeferredHolder<AbstractSpell, FireballSpell> FIREBALL = registerSpell(new FireballSpell());
    public static final DeferredHolder<AbstractSpell, SpeedEnhancementSpell> SPEED_ENHANCEMENT = registerSpell(new SpeedEnhancementSpell());


}
