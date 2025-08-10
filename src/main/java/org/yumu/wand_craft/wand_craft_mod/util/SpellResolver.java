package org.yumu.wand_craft.wand_craft_mod.util;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;
import org.yumu.wand_craft.wand_craft_mod.api.WandData;
import org.yumu.wand_craft.wand_craft_mod.registries.ComponentRegistry;

public class SpellResolver {
    private ItemStack stack;
    private Player player;
    public SpellResolver(ItemStack stack, Player player) {
        this.stack = stack;
        this.player = player;
    }

    //入口
    public boolean resolve() {
        WandData wandData = stack.get(ComponentRegistry.WAND_COMPONENT.get());
        MagicData magicData = MagicData.getPlayerMagicData(player);
        return false;
    }
}
