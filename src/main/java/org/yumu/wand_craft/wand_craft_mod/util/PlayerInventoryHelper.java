package org.yumu.wand_craft.wand_craft_mod.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.yumu.wand_craft.wand_craft_mod.api.WandData;
import org.yumu.wand_craft.wand_craft_mod.item.Wand;
import org.yumu.wand_craft.wand_craft_mod.registries.AttributeRegistry;
import org.yumu.wand_craft.wand_craft_mod.registries.ComponentRegistry;

public class PlayerInventoryHelper {
    public static void mainHandHelper(Level level){
        level.players().stream().toList().forEach(player -> {
            if(player instanceof ServerPlayer serverPlayer){
                AttributeInstance manaRegenAttr = serverPlayer.getAttribute(AttributeRegistry.MANA_REGEN_NUM);
                if(manaRegenAttr!=null){
                    ItemStack itemStack = serverPlayer.getMainHandItem();
                    WandData wandData = itemStack.get(ComponentRegistry.WAND_COMPONENT.get());
                    if(wandData != null&& wandData.isInitialized()){
                        if(!manaRegenAttr.hasModifier(Wand.getWandBonus())){
                            AttributeModifier newModifier = new AttributeModifier(
                                    Wand.getWandBonus(),
                                    wandData.getManaRegen(),
                                    AttributeModifier.Operation.ADD_VALUE
                            );
                            manaRegenAttr.addPermanentModifier(newModifier);
                        }else if(manaRegenAttr.getModifier(Wand.getWandBonus()).amount()!= wandData.getManaRegen()){
                            manaRegenAttr.removeModifier(Wand.getWandBonus());
                            AttributeModifier newModifier = new AttributeModifier(
                                    Wand.getWandBonus(),
                                    wandData.getManaRegen(),
                                    AttributeModifier.Operation.ADD_VALUE
                            );
                            manaRegenAttr.addPermanentModifier(newModifier);
                        }
                    }else{
                        if(manaRegenAttr.hasModifier(Wand.getWandBonus())){
                            manaRegenAttr.removeModifier(Wand.getWandBonus());
                        }
                    }
                }
            }
        });
    }
}
