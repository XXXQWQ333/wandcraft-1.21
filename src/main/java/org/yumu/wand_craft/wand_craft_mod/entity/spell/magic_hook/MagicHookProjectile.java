package org.yumu.wand_craft.wand_craft_mod.entity.spell.magic_hook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.AbstractMagicProjectile;
import org.yumu.wand_craft.wand_craft_mod.registries.EntityRegisry;

public class MagicHookProjectile extends AbstractMagicProjectile {

    public MagicHookProjectile(EntityType<? extends MagicHookProjectile> entityType, Level level) {
        super(entityType, level);
        this.expireTime=15*20;
    }
    public MagicHookProjectile(Level level, float speed, float damage, LivingEntity shooter){
        super(EntityRegisry.MAGIC_HOOK.get(),level,15*20,shooter);
        this.speed=speed;
        this.damage=damage;
        this.shoot(shooter.getLookAngle());
        setNoGravity(false);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.08D;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        super.onHitBlock(result);

        // 获取钩子的拥有者（必须是活体实体才能被拉动）
        Entity owner = this.getOwner();
        if (!(owner instanceof LivingEntity livingOwner) || owner.isSpectator()) {
            return;
        }

        // 获取击中的方块位置和玩家当前位置
        BlockPos blockPos = result.getBlockPos();
        Vec3 blockCenter = Vec3.atCenterOf(blockPos); // 方块中心坐标
        Vec3 playerPos = owner.position();

        // 计算从玩家到方块的向量
        Vec3 pullVector = blockCenter.subtract(playerPos).normalize();

        // 设置拉动速度（可根据需要调整）
        double pullSpeed = 1.35 * this.speed; // 使用发射速度的1.2倍作为拉动速度

        // 应用拉力（保留Y轴分量以避免玩家贴地飞行）
        livingOwner.setDeltaMovement(
                pullVector.x * pullSpeed,
                Math.max(0.3, pullVector.y) * pullSpeed, // 确保有向上的力
                pullVector.z * pullSpeed
        );

        // 触发玩家运动更新
        livingOwner.hurtMarked = true;
        // 移除钩子实体
        this.discard();
    }
}
