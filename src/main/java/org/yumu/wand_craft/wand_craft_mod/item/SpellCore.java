// org/yumu/wand_craft/wand_craft_mod/item/SpellCore.java
package org.yumu.wand_craft.wand_craft_mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import org.yumu.wand_craft.wand_craft_mod.api.SpellData;
import org.yumu.wand_craft.wand_craft_mod.registries.ComponentRegistry;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;

import java.util.List;

public class SpellCore extends Item {

    public SpellCore(Properties properties) {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    private SpellData getSpellDataFromItemStack(ItemStack stack){
        Object spell = stack.getOrDefault(ComponentRegistry.SPELL_COMPONENT, new SpellData());
        if(spell instanceof SpellData spellData){
            return spellData;
        }else{
            return new SpellData();
        }
    }



    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);


        SpellData spellData = getSpellDataFromItemStack(stack);
        AbstractSpell spell = spellData.getSpell();

        if (spell != null) {
            tooltipComponents.add(Component.translatable("spell.wandcraft." + spell.getSpellName()));
        } else {
            tooltipComponents.add(Component.literal("未绑定法术"));
        }
    }
}
