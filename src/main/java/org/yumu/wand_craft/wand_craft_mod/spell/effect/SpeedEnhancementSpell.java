package org.yumu.wand_craft.wand_craft_mod.spell.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.AbstractMagicProjectile;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractEffectSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;


/**
 * 速度法术 - 效果法术示例
 */
public class SpeedEnhancementSpell extends AbstractEffectSpell {

    private float magnification=1.25f;

    public SpeedEnhancementSpell() {
        super( 0, 10, false, "speed_enhancement");
    }

    @Override
    public void eachTick(Entity entity) {
        if(entity.tickCount!=1)return;
        if(!(entity instanceof AbstractMagicProjectile))return;
        ((AbstractMagicProjectile) entity).setSpeed(((AbstractMagicProjectile) entity).getSpeed()*magnification);

    }

    @Override
    public void onHit(Entity entity, HitResult result) {

    }

    @Override
    public void inEnd(Entity entity) {

    }

    @Override
    public AbstractSpell Copy() {
        return new SpeedEnhancementSpell();
    }



}
