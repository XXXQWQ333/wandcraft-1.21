package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.gui.Arcane_Engraver.ArcaneEngraverMenu;


public class MenuRegistry {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, WandCraft.MODID);

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

    public static final DeferredHolder<MenuType<?>, MenuType<ArcaneEngraverMenu>> ARCANE_ENGRAVER_MENU =
            MENUS.register("arcane_engraver", () -> IMenuTypeExtension.create(ArcaneEngraverMenu::new));

}
