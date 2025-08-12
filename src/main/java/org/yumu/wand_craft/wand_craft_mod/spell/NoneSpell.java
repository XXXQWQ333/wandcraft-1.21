package org.yumu.wand_craft.wand_craft_mod.spell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class NoneSpell extends AbstractEffectSpell {

    public NoneSpell() {
        super("none", 0, 0, false, "none");
    }

    @Override
    public void eachTick(Entity entity) {

    }

    @Override
    public void onHit(Entity Projectile, HitResult result) {

    }

    @Override
    public void inEnd(Entity entity) {

    }

    @Override
    public AbstractSpell Copy() {
        return new NoneSpell();
    }
}
