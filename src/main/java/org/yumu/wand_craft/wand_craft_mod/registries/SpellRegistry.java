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
import org.yumu.wand_craft.wand_craft_mod.spell.projectile.*;

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

    //TODO:
    /**
     * Chain lightning spell：连锁闪电法术
     * laser spell：激光法术
     * ball lightning spell：球状闪电法术
     * cloud thunder spell：雷云法术
     * meteor rain spell：流星雨法术
     * reverse teleport spell：传送法术
     * random projectile spell：随机投掷物法术
     */
    //投掷物法术注册
    public static final DeferredHolder<AbstractSpell, FireballSpell> FIREBALL = registerSpell(new FireballSpell());
    public static final DeferredHolder<AbstractSpell, MiniBombSpell> MINI_BOMB = registerSpell(new MiniBombSpell());
    public static final DeferredHolder<AbstractSpell, BombSpell> BOMB = registerSpell(new BombSpell());
    public static final DeferredHolder<AbstractSpell, MagicArrowSpell> MAGIC_ARROW = registerSpell(new MagicArrowSpell());
    public static final DeferredHolder<AbstractSpell, MagicHookSpell> MAGIC_HOOK = registerSpell(new MagicHookSpell());




    //TODO:
    /**
     * multicasting spell：多重施法法术
     * duration extension spell：持续时间延长法术
     * duration reduction spell：持续时间减少法术
     * mana consumption reduction spell：魔力消耗减少法术
     * speed reduction spell：速度减少法术
     * downward duplication spell：向下复制法术
     * gravity pull spell: 重力拉扯法术
     * gravity enhancement spell: 重力增强法术
     * gravity reduction spell: 重力减少法术
     * arc Lightning spell: 电弧法术
     * fire trail spell：火焰轨迹法术
     */
    //效果法术注册
    public static final DeferredHolder<AbstractSpell, SpeedEnhancementSpell> SPEED_ENHANCEMENT = registerSpell(new SpeedEnhancementSpell());


}
