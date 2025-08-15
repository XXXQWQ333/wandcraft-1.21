package org.yumu.wand_craft.wand_craft_mod.damage;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.fml.common.EventBusSubscriber;


public class DamageSources {
    public static DamageSource get(Level level, ResourceKey<DamageType> damageType){
        return level.damageSources().source(damageType);
    }

    public static boolean applyDamage(Entity entity, DamageSource damageSource, float amount){
        return entity.hurt(damageSource, amount);
    }

}
