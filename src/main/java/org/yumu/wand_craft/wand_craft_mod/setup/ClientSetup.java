package org.yumu.wand_craft.wand_craft_mod.setup;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.registries.MenuRegistry;
import org.yumu.wand_craft.wand_craft_mod.gui.Arcane_Engraver.ArcaneEngraverScreen;


@EventBusSubscriber(modid = WandCraft.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerMenuScreen(RegisterMenuScreensEvent event){
        event.register(MenuRegistry.ARCANE_ENGRAVER_MENU.get(), ArcaneEngraverScreen::new);
    }
}
