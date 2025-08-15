package org.yumu.wand_craft.wand_craft_mod.util;


import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;
import org.yumu.wand_craft.wand_craft_mod.api.WandData;
import org.yumu.wand_craft.wand_craft_mod.registries.ComponentRegistry;
import org.yumu.wand_craft.wand_craft_mod.registries.SpellRegistry;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractEffectSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractProjectileSpell;

import java.util.ArrayList;
import java.util.List;

public class SpellResolver {
    private ItemStack stack;
    private Player player;
    private WandData wandData;
    private MagicData magicData;
    private int newIndex;

    private int sumCostMana=0;
    List<AbstractEffectSpell> subEffectSpells = new ArrayList<>();
    List<AbstractProjectileSpell> subProjectileSpells = new ArrayList<>();
    public SpellResolver(ItemStack stack, Player player) {
        this.stack = stack;
        this.player = player;
        this.wandData = stack.get(ComponentRegistry.WAND_COMPONENT.get());
        this.magicData = MagicData.getPlayerMagicData(player);
        List<AbstractSpell> spells=new ArrayList<>();
        if (wandData.getSpellIds() != null) {
            wandData.getSpellIds().stream().forEach(spellId -> {
                spells.add(SpellRegistry.getSpell(spellId).Copy());
            });
        }
        spellsAnalysis(spells);
    }
    //解析法术，单次法杖释放的法术
    private boolean spellsAnalysis(List<AbstractSpell> spells){
        if(spells==null || spells.size()<=0)return false;

        int costCastCount= wandData.getCastCount();
        subProjectileSpells = new ArrayList<>();
        subEffectSpells = new ArrayList<>();
        newIndex=wandData.getIndex();
        int empC=0;//用于记录空法术（非投掷物法术）
        //单次释放法术的个数不能超过法杖最大栏位的2倍
        for (;;newIndex++){


            if(costCastCount<=0||empC> wandData.getMaxSpellSlot())break;
            AbstractSpell spell = spells.get(newIndex%wandData.getMaxSpellSlot());
            if(SpellRegistry.getSpellId( spell.getSpellName()).equals(SpellRegistry.NONE.getId())){
                empC++;
                continue;
            }
            if(spell instanceof AbstractProjectileSpell) {
                subProjectileSpells.add((AbstractProjectileSpell) spell);
                empC=0;
            }
            else if(spell instanceof AbstractEffectSpell) {
                empC++;
                subEffectSpells.add((AbstractEffectSpell) spell);
            }

            costCastCount-=spell.getCostCastCount();
            sumCostMana+=spell.getCostMana();
            //达到最大施法上限
            if(newIndex==wandData.getMaxSpellSlot()*2-1){
                break;
            }
        }


        return true;
    }
    //入口
    public boolean resolve() {

        //检查是否解析以及是否够法力释放
        if(subProjectileSpells.isEmpty()||subProjectileSpells==null||magicData.getMana()<=sumCostMana) return false;

        //释放法术
        for (AbstractProjectileSpell spell : subProjectileSpells){
            spell.setSubEffectSpells(subEffectSpells);
            spell.onCast(stack, player.level(), player, player.getUsedItemHand());
        }
        player.getCooldowns().addCooldown(stack.getItem(), wandData.getCoolDownTime());
        //扣除法力
        magicData.setMana(magicData.getMana()-sumCostMana);

        //判断是否走完整条法术链
        if (newIndex%wandData.getMaxSpellSlot()<= wandData.getIndex()||newIndex==wandData.getMaxSpellSlot()*2-1){
            reload();

        }else{
            wandData.setIndex(newIndex%wandData.getMaxSpellSlot());
        }
        player.sendSystemMessage(Component.literal("newIndex:"+newIndex%wandData.getMaxSpellSlot()));

        return true;
    }
    private void reload(){
        wandData.setIndex(0);
        player.getCooldowns().addCooldown(stack.getItem(), wandData.getCoolDownTime());
    }
}
