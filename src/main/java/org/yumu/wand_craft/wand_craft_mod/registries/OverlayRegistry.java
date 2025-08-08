// org/yumu/wand_craft/wand_craft_mod/registries/OverlayRegistry.java
package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.gui.overlays.ManaBar;

@EventBusSubscriber(modid = WandCraft.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class OverlayRegistry {
    @SubscribeEvent
    public static void onRegisterOverlays(RegisterGuiLayersEvent event) {
        // 注册魔力条覆盖层
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR,
                ResourceLocation.parse(WandCraft.MODID + ":mana_bar"),
            new ManaBar());
    }
}
