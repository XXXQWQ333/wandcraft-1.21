// org/yumu/wand_craft/wand_craft_mod/event/MagicEvents.java
package org.yumu.wand_craft.wand_craft_mod.event;

import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.capability.MagicManager;
import org.yumu.wand_craft.wand_craft_mod.item.Wand;

public class MagicEvents  {

    public static void onWorldTick(LevelTickEvent.Pre event){
        // 客户端不执行任何操作
        if (event.getLevel().isClientSide) {
            return;
        }
        WandCraft.MAGIC_MANAGER.tick(event.getLevel());
//        WandCraft.LOGGER.info("tick!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
