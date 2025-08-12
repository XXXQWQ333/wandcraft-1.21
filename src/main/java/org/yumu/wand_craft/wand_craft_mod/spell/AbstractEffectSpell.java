// IeffectSpell.java
package org.yumu.wand_craft.wand_craft_mod.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.yumu.wand_craft.wand_craft_mod.registries.SpellRegistry;

/**
 * 效果法术接口
 * 实现此接口的法术可以修改投射物法术的行为
 */
public abstract class AbstractEffectSpell extends AbstractSpell{

    public AbstractEffectSpell(String spellId, int costCastCount, int costMana, boolean isBlockPoint, String spellName) {
        super(spellId, costCastCount, costMana, isBlockPoint, spellName);
    }


    //法术每一帧执行
    public abstract void eachTick(Entity entity);
    //法术击中时执行
    public abstract void onHit(Entity entity, HitResult result);
    //法术生命周期结束时执行
    public abstract void inEnd(Entity entity);

}
