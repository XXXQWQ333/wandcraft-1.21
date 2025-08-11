package org.yumu.wand_craft.wand_craft_mod.spell.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.AbstractMagicProjectile;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractEffectSpell;

import java.lang.reflect.Method;


/**
 * 速度法术 - 效果法术示例
 */
public class SpeedEnhancementSpell extends AbstractEffectSpell {

    private float magnification=1.5f;

    public SpeedEnhancementSpell() {
        super("speed_enhancement", 0, 1, false, "speed_enhancement");
    }

    @Override
    protected void eachTick(Entity entity) {
        if(entity.tickCount!=1)return;
        if(!(entity instanceof AbstractMagicProjectile))return;
        ((AbstractMagicProjectile) entity).setSpeed(((AbstractMagicProjectile) entity).getSpeed()*magnification);

//        if (canChangeSpeed(entity, "setSpeed", void.class)) {
//            // 调用 setSpeed 方法
//            try {
//
//                Method method = entity.getClass().getMethod("setSpeed", void.class);
//                method.invoke(entity, 1.5f); // 调用方法并传入参数 1.5f
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    protected void onHit(Entity entity, HitResult result) {

    }

    @Override
    protected void inEnd(Entity entity) {

    }
//    public static boolean canChangeSpeed(Object obj, String methodName, Class<?>... parameterTypes) {
//        try {
//            // 获取对象的类
//            Class<?> clazz = obj.getClass();
//            // 获取指定的方法
//            Method method = clazz.getMethod(methodName, parameterTypes);
//            return true; // 如果方法存在，返回 true
//        } catch (NoSuchMethodException e) {
//            return false; // 如果方法不存在，返回 false
//        }
//    }


}
