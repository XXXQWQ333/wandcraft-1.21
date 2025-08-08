// 修改 org/yumu/wand_craft/wand_craft_mod/registries/EventRegistry.java 文件
package org.yumu.wand_craft.wand_craft_mod.registries;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import org.yumu.wand_craft.wand_craft_mod.event.MagicEvents;

public class EventRegistry {
    public static void setup(){
        IEventBus bus = NeoForge.EVENT_BUS;
        bus.addListener(MagicEvents::onWorldTick);
    }
}
