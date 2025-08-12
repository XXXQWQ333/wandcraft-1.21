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
    private List<AbstractSpell> spells=new ArrayList<>();
    private int sumCostMana=0;
    List<AbstractEffectSpell> subEffectSpells = new ArrayList<>();
    List<AbstractProjectileSpell> subProjectileSpells = new ArrayList<>();
    public SpellResolver(ItemStack stack, Player player) {
        this.stack = stack;
        this.player = player;
        this.wandData = stack.get(ComponentRegistry.WAND_COMPONENT.get());
        if (wandData.getSpellIds() != null) {
            wandData.getSpellIds().stream().forEach(spellId -> {
                spells.add(SpellRegistry.getSpell(spellId).Copy());
            });
        }
    }
    //解析法术，单次法杖释放的法术
    private boolean spellsAnalysis(){
        if(spells==null || spells.size()<=0)return false;

        int costCastCount= wandData.getCastCount();
        subProjectileSpells = new ArrayList<>();
        subEffectSpells = new ArrayList<>();
        for (AbstractSpell spell : spells){
            if(costCastCount<=0)break;
            if(spell.isBlockPoint())break;
            if(spell.getSpellId().equals(SpellRegistry.NONE.getId())){
                continue;
            }

            if(spell instanceof AbstractProjectileSpell) subProjectileSpells.add((AbstractProjectileSpell) spell);
            else if(spell instanceof AbstractEffectSpell) subEffectSpells.add((AbstractEffectSpell) spell);
            costCastCount-=spell.getCostCastCount();
            sumCostMana+=spell.getCostMana();
        }
        return true;
    }
//    private boolean assemblingSpells(){
//        MagicData magicData = MagicData.getPlayerMagicData(player);
//        if(magicData.getMana()>=sumCostMana){
//            if(subEffectSpells.size()>0){
//                for (AbstractSpell spell : subProjectileSpells){
//                    spell.setNextSpellId(subEffectSpells.get(0).getSpellId());
//                }
//            }
//            return true;
//        }
//        return false;
//    }
    //入口
    public boolean resolve() {
        //解析法术
        if(spellsAnalysis()){
            for (AbstractProjectileSpell spell : subProjectileSpells){
//                    player.sendSystemMessage(Component.literal("当前魔法: " + spell));
                spell.setSubEffectSpells(subEffectSpells);
                spell.onCast(stack, player.level(), player, player.getUsedItemHand());
            }
        }

        return false;
    }
}
