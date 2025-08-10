// org/yumu/wand_craft/wand_craft_mod/item/Wand.java
package org.yumu.wand_craft.wand_craft_mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;
import org.yumu.wand_craft.wand_craft_mod.api.WandData;
import org.yumu.wand_craft.wand_craft_mod.registries.ComponentRegistry;
import org.yumu.wand_craft.wand_craft_mod.util.SpellResolver;

import java.util.List;

public class Wand extends Item {



    public Wand(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        // 检查组件是否已注册
        if (ComponentRegistry.WAND_COMPONENT.isBound()) {
            // 获取法杖数据
            WandData wandData = stack.get(ComponentRegistry.WAND_COMPONENT.get());

            // 如果法杖已有数据，显示法术槽数量
            if (wandData != null) {
                tooltipComponents.add(Component.translatable("wandcraft.wand.spell_slots", wandData.getMaxSpellSlot()));
            } else {
                // 如果法杖还没有数据，显示未初始化
                tooltipComponents.add(Component.literal("未初始化的法杖"));
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            // 检查组件是否已注册
            if (ComponentRegistry.WAND_COMPONENT.isBound()) {
                // 检查法杖是否已初始化
                WandData wandData = stack.get(ComponentRegistry.WAND_COMPONENT.get());

                // 如果法杖未初始化，则进行初始化
                if (wandData == null) {

                    // 创建新的法杖数据
                    wandData = new WandData();
                    wandData.recast();
                    // 将数据附加到物品上
                    stack.set(ComponentRegistry.WAND_COMPONENT.get(), wandData);
                    // 向玩家发送消息
                    player.sendSystemMessage(Component.literal("法杖已初始化，最大法术槽数量: " + wandData.getMaxSpellSlot()));
                }
            }

            // 获取玩家的魔法数据
            MagicData magicData = MagicData.getPlayerMagicData(player);
            float currentMana = magicData.getMana();
            magicData.addMana(-5);

            // 向玩家发送当前 mana 值的消息
            player.sendSystemMessage(Component.literal("当前魔法值: " + currentMana));
        }
        return InteractionResultHolder.success(stack);
    }
    private boolean castSpell(ItemStack stack, Player player) {
        SpellResolver resolver = new SpellResolver(stack, player);
        return resolver.resolve();
    }
}


