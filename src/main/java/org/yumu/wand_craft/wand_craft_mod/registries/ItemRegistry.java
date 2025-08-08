// file: org/yumu/wand_craft/wand_craft_mod/registries/ItemRegistry.java
package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.item.Wand;

public class ItemRegistry {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, WandCraft.MODID);
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    /**
     *
     * items
     */
    public static final DeferredHolder<Item, Item> EXAMPLE_ITEM = ITEMS.register("example_item",()->new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block_item",()->new BlockItem(BlockRegistry.EXAMPLE_BLOCK.get(),new Item.Properties()));

    // 注册魔杖物品
    public static final DeferredHolder<Item, Item> WAND = ITEMS.register("wand", () -> new Wand(new Item.Properties().stacksTo(1)));
}
