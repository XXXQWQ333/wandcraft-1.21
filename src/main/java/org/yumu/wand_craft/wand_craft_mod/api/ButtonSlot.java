package org.yumu.wand_craft.wand_craft_mod.api;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * 按钮槽位类，用于处理打印按钮的显示逻辑
 */
public class ButtonSlot extends Slot {

    public ButtonSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false; // 不允许放置物品
    }

    @Override
    public boolean mayPickup(Player player) {
        return false; // 不允许取出物品
    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY; // 返回空物品
    }
}
