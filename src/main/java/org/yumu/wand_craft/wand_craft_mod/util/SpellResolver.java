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
        int empC=0;
        for (;;newIndex++){
            newIndex%= wandData.getMaxSpellSlot();
            if(costCastCount<=0||empC> wandData.getMaxSpellSlot())break;
            AbstractSpell spell = spells.get(newIndex);
            if(spell.getSpellId().equals(SpellRegistry.NONE.getId())){
                empC++;
                continue;
            }
            if(spell instanceof AbstractProjectileSpell) {
                subProjectileSpells.add((AbstractProjectileSpell) spell);
                empC++;
            }
            else if(spell instanceof AbstractEffectSpell) {
                empC=0;
                subEffectSpells.add((AbstractEffectSpell) spell);
            }
            costCastCount-=spell.getCostCastCount();
            sumCostMana+=spell.getCostMana();
        }


        return true;
    }
    //入口
    public boolean resolve() {

        if(subProjectileSpells.isEmpty()||subProjectileSpells==null||magicData.getMana()<=sumCostMana) return false;

        for (AbstractProjectileSpell spell : subProjectileSpells){
            spell.setSubEffectSpells(subEffectSpells);
            spell.onCast(stack, player.level(), player, player.getUsedItemHand());
        }
        magicData.setMana(magicData.getMana()-sumCostMana);

        if (newIndex<= wandData.getIndex()){
            player.getCooldowns().addCooldown(stack.getItem(), wandData.getCoolDownTime());
        }
        wandData.setIndex(newIndex);

        return true;
    }
}
