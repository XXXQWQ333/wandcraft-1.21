package org.yumu.wand_craft.wand_craft_mod.entity.spell.magic_missile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.AbstractMagicProjectile;
import org.yumu.wand_craft.wand_craft_mod.registries.EntityRegisry;

public class MagicArrowProjectile extends AbstractMagicProjectile {
    public MagicArrowProjectile(EntityType<? extends MagicArrowProjectile> entityType, Level level) {
        super(entityType, level);
        this.expireTime=15*20;
    }
    public MagicArrowProjectile(Level level, float speed, float damage, Entity shooter){
        super(EntityRegisry.MAGIC_ARROW.get(),level,15*20,shooter);
        this.speed=speed;
        this.damage=damage;
        this.shoot(shooter.getLookAngle());

        setNoGravity(false);
    }

    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().magic(), (float) (this.damage));
        discard();
    }
}
