package org.yumu.wand_craft.wand_craft_mod.entity.spell.magic_missile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.AbstractMagicProjectile;
import org.yumu.wand_craft.wand_craft_mod.registries.EntityRegisry;

public class MagicArrowProjectile extends AbstractMagicProjectile {
    public MagicArrowProjectile(EntityType<? extends MagicArrowProjectile> entityType, Level level) {
        super(entityType, level);
        this.expireTime=15*20;
    }
    public MagicArrowProjectile(Level level, float speed, float damage, LivingEntity shooter){
        super(EntityRegisry.MAGIC_ARROW.get(),level);
        this.speed=speed;
        this.damage=damage;
        this.shoot(shooter.getLookAngle());
        this.expireTime=15*20;
        setNoGravity(true);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
//        this.discard();
    }
}
