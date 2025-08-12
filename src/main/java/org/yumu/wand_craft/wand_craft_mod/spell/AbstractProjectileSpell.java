package org.yumu.wand_craft.wand_craft_mod.spell;

//在如今架构上帮我设计一个架构，使得： 具体的法术类需要继承AbstractSpell和其中的一个接口。 释放的投射类法术要收到效果影响类法术的影响，具体的影响在具体影响类法术内定义

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.AbstractMagicProjectile;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.mini_bomb.MiniBombProjectile;

import java.util.ArrayList;
import java.util.List;

/**
 * 投射物法术接口
 * 实现此接口的法术可以被效果法术影响
 */
public abstract class AbstractProjectileSpell extends AbstractSpell {

    protected AbstractMagicProjectile projectile;
    protected List<AbstractEffectSpell> subEffectSpells=null;

    public AbstractProjectileSpell(String spellId, int costCastCount, int costMana, boolean isBlockPoint, String spellName) {
        super(spellId, costCastCount, costMana, isBlockPoint, spellName);
    }


    /**
     * 法术释放
     *设置投射物的初始位置，方向，负责生成对应属性的投射物
     * 播放声音等
     */
    public abstract void onCast(ItemStack stack, Level level, Player player, InteractionHand hand);

    public void setSubEffectSpells(final List<AbstractEffectSpell> subEffectSpells) {
        this.subEffectSpells = subEffectSpells;
    }
}