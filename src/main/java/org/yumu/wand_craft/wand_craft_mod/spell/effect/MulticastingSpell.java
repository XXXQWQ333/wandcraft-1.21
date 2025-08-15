package org.yumu.wand_craft.wand_craft_mod.spell.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractEffectSpell;

public class MulticastingSpell extends AbstractEffectSpell {
    public MulticastingSpell() {
        super( -1, 10, false,"muticasting_spell");
    }

    @Override
    public void eachTick(Entity entity) {

    }

    @Override
    public void onHit(Entity entity, HitResult result) {

    }

    @Override
    public void inEnd(Entity entity) {

    }
}
