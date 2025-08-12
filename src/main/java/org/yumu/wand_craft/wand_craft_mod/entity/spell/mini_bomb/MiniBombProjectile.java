package org.yumu.wand_craft.wand_craft_mod.entity.spell.mini_bomb;



import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.AbstractMagicProjectile;
import org.yumu.wand_craft.wand_craft_mod.registries.EntityRegisry;

import java.util.List;

public class MiniBombProjectile extends AbstractMagicProjectile {



    public MiniBombProjectile(EntityType<? extends MiniBombProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public MiniBombProjectile(Level level, float speed, float damage, LivingEntity shooter) {
        super(EntityRegisry.MINI_BOMB.get(), level);
        this.speed = speed;
        this.damage = damage;
        this.shoot(shooter.getLookAngle());
        this.expireTime=15*20;
    }

    @Override
    protected double getDefaultGravity() {
        return 5.0;
    }

    @Override
    protected void onHit(HitResult result) {
        float speed = getSpeed();
        int count=this.tickCount;
        super.onHit(result);
        // 只在服务端执行爆炸逻辑
        if (!level().isClientSide) {
            // 创建爆炸效果 - 使用较小的爆炸半径和力量
            level().explode(
                    this,                    // 爆炸源实体
                    this.getX(),            // 爆炸X坐标
                    this.getY(),            // 爆炸Y坐标
                    this.getZ(),            // 爆炸Z坐标
                    1.0F,                   // 爆炸半径(小型炸弹应该较小)
                    Level.ExplosionInteraction.NONE  // 不破坏方块，只造成伤害
            );
            // 获取附近的实体并造成伤害
            AABB explosionArea = this.getBoundingBox().inflate(2.0D, 2.0D, 2.0D);
            List<Entity> entities = level().getEntities(this, explosionArea);

            for (Entity entity : entities) {
                if (entity instanceof LivingEntity livingEntity && canHitEntity(entity)) {
                    // 计算距离衰减
                    double distance = this.distanceTo(entity);
                    double damageMultiplier = Math.max(0.0D, 1.0D - distance / 2.0D);

                    if (damageMultiplier > 0.0D) {
                        entity.hurt(
                                this.damageSources().magic(),
                                (float) (this.damage * damageMultiplier)
                        );
                    }
                }
            }
            // 移除投射物
            this.discard();
        }
    }
}
