package org.yumu.wand_craft.wand_craft_mod.entity.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.yumu.wand_craft.wand_craft_mod.registries.SpellRegistry;

import org.yumu.wand_craft.wand_craft_mod.spell.AbstractEffectSpell;


public abstract class AbstractMagicProjectile extends Projectile {

    protected ResourceLocation spellId=null;
    protected int expireTime;
    protected float damage;
    protected float speed;

    public float getSpeed() {
        return speed;
    }
    public float setSpeed(float speed){
        return this.speed = speed;
    }
    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }


    public int getExpireTime() {
        return expireTime;
    }

    protected AbstractMagicProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }


    public void shoot(Vec3 rotation){
        setDeltaMovement( rotation.scale(getSpeed()));
    }

    @Override
    protected double getDefaultGravity() {
        return 0.1;
    }

    @Override
    protected boolean canHitEntity(Entity target) {
        var owner = getOwner();
        return super.canHitEntity(target) && target != owner && (owner == null || !owner.isAlliedTo(target));
    }


    protected void DecorateEachTick(Entity entity){
        if(spellId==null) return;
        if(!(SpellRegistry.getSpell(spellId) instanceof AbstractEffectSpell))return;
        AbstractEffectSpell spell = (AbstractEffectSpell)SpellRegistry.getSpell(spellId);
        spell.DecorateEachTick(entity);
    }
    protected void DecorateonHit(Entity Projectile,HitResult result){
        if(spellId==null) return;
        if(!(SpellRegistry.getSpell(spellId) instanceof AbstractEffectSpell))return;
        AbstractEffectSpell spell = (AbstractEffectSpell)SpellRegistry.getSpell(spellId);
        spell.DecorateonHit(Projectile,result);
    }
    protected void DecorateInEnd(Entity entity){
        if(spellId==null) return;
        if(!(SpellRegistry.getSpell(spellId) instanceof AbstractEffectSpell))return;
        AbstractEffectSpell spell = (AbstractEffectSpell)SpellRegistry.getSpell(spellId);
        spell.DecorateInEnd(entity);
    }

    @Override
    public void tick() {
        super.tick();
        DecorateEachTick(this);
        if(tickCount>expireTime){
            DecorateInEnd(this);
        }
        if(tickCount>expireTime){
            discard();
            return;
        }
        hitDetection();
        handleMove();
    }
    protected void handleMove(){
        // 更新实体位置
        setPos(position().add(getDeltaMovement()));

        // 计算并设置实体朝向
        Vec3 motion = getDeltaMovement();
        double atan2Y = Math.atan2(motion.horizontalDistance(), motion.y);
        double atan2XZ = Math.atan2(motion.z, motion.x);
        float xRot = Mth.wrapDegrees(-(float) (atan2Y * (180F / Math.PI)) - 90.0F);
        float yRot = Mth.wrapDegrees(-(float) (atan2XZ * (180F / Math.PI)) + 90.0F);
        setXRot(xRot);
        setYRot(yRot);

        // 应用重力（如果受重力影响）
        if (!isNoGravity()) {
            setDeltaMovement(getDeltaMovement().add(0, -getDefaultGravity(), 0));
        }
    }

    public void hitDetection(){
        HitResult hitResult= ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if(hitResult.getType()!=HitResult.Type.MISS){
            onHit(hitResult);

        }
    }
    /*只在弹射物命中实体时触发*/
    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
    }

    /*通用命中处理：无论命中的是实体还是方块都会触发
     播放音效和粒子效果：在服务端生成命中粒子和播放音效
     基础命中逻辑：处理所有命中情况下的通用行为
     */
    @Override
    protected void onHit(HitResult result) {
        DecorateonHit(this,result);
        super.onHit(result);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("damage", damage);
        compound.putInt("tickCount", tickCount);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.damage=compound.getFloat("damage");
        this.tickCount=compound.getInt("tickCount");
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }
}
