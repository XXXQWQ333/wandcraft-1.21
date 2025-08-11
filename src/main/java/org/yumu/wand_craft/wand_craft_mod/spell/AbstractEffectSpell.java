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

    protected ResourceLocation nextSpellId=null;

    /**
     * 构造一个新的法术对象
     *
     * @param spellId       法术的名称
     * @param costCastCount
     * @param costMana
     * @param isBlockPoint
     * @param spellName
     */
    public AbstractEffectSpell(String spellId, int costCastCount, int costMana, boolean isBlockPoint, String spellName) {
        super(spellId, costCastCount, costMana, isBlockPoint, spellName);
    }

    public ResourceLocation getSpellId() {
        return nextSpellId;
    }

    public void setSpellId(ResourceLocation spellId) {
        this.nextSpellId = spellId;
    }


    public void DecorateEachTick(Entity entity){
        eachTick( entity);
        if(nextSpellId==null) return;
        if(!(SpellRegistry.getSpell(nextSpellId) instanceof AbstractEffectSpell))return;
        AbstractEffectSpell spell = (AbstractEffectSpell)SpellRegistry.getSpell(nextSpellId);
        spell.DecorateEachTick(entity);
    }
    public void DecorateonHit(Entity entity, HitResult result){
        onHit(entity, result);
        if(nextSpellId==null) return;
        if(!(SpellRegistry.getSpell(nextSpellId) instanceof AbstractEffectSpell))return;
        AbstractEffectSpell spell = (AbstractEffectSpell)SpellRegistry.getSpell(nextSpellId);
        spell.DecorateonHit(entity, result);
    }
    public void DecorateInEnd(Entity entity){
        inEnd(entity);
        if(nextSpellId==null) return;
        if(!(SpellRegistry.getSpell(nextSpellId) instanceof AbstractEffectSpell))return;
        AbstractEffectSpell spell = (AbstractEffectSpell)SpellRegistry.getSpell(nextSpellId);
        spell.DecorateInEnd(entity);
    }

    //法术每一帧执行
    protected abstract void eachTick(Entity entity);
    //法术击中时执行
    protected abstract void onHit(Entity entity, HitResult result);
    //法术生命周期结束时执行
    protected abstract void inEnd(Entity entity);

}
