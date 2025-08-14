// org/yumu/wand_craft/wand_craft_mod/event/MagicEvents.java
package org.yumu.wand_craft.wand_craft_mod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;
import org.yumu.wand_craft.wand_craft_mod.api.WandData;
import org.yumu.wand_craft.wand_craft_mod.capability.MagicManager;
import org.yumu.wand_craft.wand_craft_mod.item.Wand;
import org.yumu.wand_craft.wand_craft_mod.registries.AttributeRegistry;
import org.yumu.wand_craft.wand_craft_mod.registries.ComponentRegistry;
import org.yumu.wand_craft.wand_craft_mod.util.PlayerInventoryHelper;

public class MagicEvents  {

    public static void onWorldTick(LevelTickEvent.Pre event){
        // 客户端不执行任何操作
        if (event.getLevel().isClientSide) {
            return;
        }
        WandCraft.MAGIC_MANAGER.tick(event.getLevel());
    }
    public static void onPlayerTick(LevelTickEvent.Pre event){
        if (event.getLevel().isClientSide) {
            return;
        }
        PlayerInventoryHelper.mainHandHelper(event.getLevel());

    }

}
