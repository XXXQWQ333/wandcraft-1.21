package org.yumu.wand_craft.wand_craft_mod.setup;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.bomb.BombProjectileRenderer;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.magic_missile.MagicArrowProjectileRenderer;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.mini_bomb.MiniBombProjectileRenderer;
import org.yumu.wand_craft.wand_craft_mod.registries.EntityRegisry;
import org.yumu.wand_craft.wand_craft_mod.registries.MenuRegistry;
import org.yumu.wand_craft.wand_craft_mod.gui.Arcane_Engraver.ArcaneEngraverScreen;


@EventBusSubscriber(modid = WandCraft.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerMenuScreen(RegisterMenuScreensEvent event){
        event.register(MenuRegistry.ARCANE_ENGRAVER_MENU.get(), ArcaneEngraverScreen::new);
    }


    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(EntityRegisry.MINI_BOMB.get(), MiniBombProjectileRenderer::new);
        event.registerEntityRenderer(EntityRegisry.BOMB.get(), BombProjectileRenderer::new);
        event.registerEntityRenderer(EntityRegisry.MAGIC_ARROW.get(), MagicArrowProjectileRenderer::new);
    }
}
