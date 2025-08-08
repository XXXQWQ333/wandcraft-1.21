// org/yumu/wand_craft/wand_craft_mod/item/Wand.java
package org.yumu.wand_craft.wand_craft_mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;

public class Wand extends Item {
    public Wand(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            // 获取玩家的魔法数据
            MagicData magicData = MagicData.getPlayerMagicData(player);
            float currentMana = magicData.getMana();
            magicData.addMana(-5);

            // 向玩家发送当前 mana 值的消息
            player.sendSystemMessage(Component.literal("当前魔法值: " + currentMana));
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
