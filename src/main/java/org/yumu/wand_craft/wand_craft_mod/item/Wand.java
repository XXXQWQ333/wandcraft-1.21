// org/yumu/wand_craft/wand_craft_mod/item/Wand.java
package org.yumu.wand_craft.wand_craft_mod.item;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.api.WandData;
import org.yumu.wand_craft.wand_craft_mod.registries.AttributeRegistry;
import org.yumu.wand_craft.wand_craft_mod.registries.ComponentRegistry;
import org.yumu.wand_craft.wand_craft_mod.util.SpellResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Wand extends Item {
    private static final ResourceLocation WAND_BONUS = ResourceLocation.fromNamespaceAndPath(WandCraft.MODID, "wand_nonus");


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
                tooltipComponents.add(Component.translatable("wandcraft.wand.mana_regen",wandData.getManaRegen()));
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
                WandData wandData = stack.get(ComponentRegistry.WAND_COMPONENT.get());
                // 如果法杖未初始化，则进行初始化
                if (wandData == null || !wandData.isInitialized()) {
                    initializeWand(wandData,stack, player);
                }
            }
            if(player.getCooldowns().isOnCooldown(this)){
                return InteractionResultHolder.fail(stack);
            }
            //如果已经注册
            castSpell(stack, player);
//            player.getCooldowns().addCooldown(stack.getItem(), 20);
        }
        return InteractionResultHolder.success(stack);
    }
    private boolean castSpell(ItemStack stack, Player player) {
        SpellResolver resolver = new SpellResolver(stack, player);
        return resolver.resolve();
    }

    public static boolean isInitialized(ItemStack stack) {
        return stack.get(ComponentRegistry.WAND_COMPONENT.get()) != null && stack.get(ComponentRegistry.WAND_COMPONENT.get()).isInitialized();
    }


    public void initializeWand(WandData wandData ,ItemStack stack, Player player){
        // 创建新的法杖数据
        wandData = new WandData();
        wandData.initialized();
        // 确保 spellIds 被正确初始化
        if (wandData.getSpellIds() == null) {
            wandData.setSpellIds(new ArrayList<>());
        }
        // 将数据附加到物品上
        stack.set(ComponentRegistry.WAND_COMPONENT.get(), wandData);
        // 向玩家发送消息
        player.sendSystemMessage(Component.literal("法杖已初始化，最大法术槽数量: " + wandData.getMaxSpellSlot()));
    }

    public static ResourceLocation getWandBonus() {
        return WAND_BONUS;
    }
}


