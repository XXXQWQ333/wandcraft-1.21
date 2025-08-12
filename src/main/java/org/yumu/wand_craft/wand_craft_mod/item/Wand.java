// org/yumu/wand_craft/wand_craft_mod/item/Wand.java
package org.yumu.wand_craft.wand_craft_mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.phys.Vec3;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;
import org.yumu.wand_craft.wand_craft_mod.api.WandData;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.mini_bomb.MiniBombProjectile;
import org.yumu.wand_craft.wand_craft_mod.registries.ComponentRegistry;
import org.yumu.wand_craft.wand_craft_mod.registries.SpellRegistry;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;
import org.yumu.wand_craft.wand_craft_mod.util.SpellResolver;

import java.util.ArrayList;
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
                if (wandData == null || !wandData.isInitialized()) {
                    // 创建新的法杖数据
                    wandData = new WandData();
                    wandData.recast();
                    // 确保 spellIds 被正确初始化
                    if (wandData.getSpellIds() == null) {
                        wandData.setSpellIds(new ArrayList<>());
                    }
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

            WandData wandData = stack.get(ComponentRegistry.WAND_COMPONENT.get());
            if (wandData != null) {
                List<AbstractSpell> spells = new ArrayList<>();
                if (wandData.getSpellIds() != null) {
                    wandData.getSpellIds().stream().forEach(spellId -> {
                        if (spellId != null) {
                            spells.add(SpellRegistry.getSpell(spellId));
                        }
                    });
                }

                // 向玩家发送当前 mana 值的消息
//                player.sendSystemMessage(Component.literal("当前魔法值: " + currentMana));
//                player.sendSystemMessage(Component.literal("当前存储的魔法: " + spells));
            }
            castSpell(stack, player);

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
//    private void castSpell(ItemStack stack,Level level, Player player, InteractionHand hand) {
//
//        MiniBombProjectile miniBombProjectile = new MiniBombProjectile(level,10,5, player);
//        Vec3 spawn = player.getEyePosition().add(player.getForward());
//        miniBombProjectile.moveTo(spawn.x, spawn.y - miniBombProjectile.getBoundingBox().getYsize() / 2, spawn.z, miniBombProjectile.getYRot() + 180, miniBombProjectile.getXRot());
//
//        level.addFreshEntity(miniBombProjectile);
//    }


}


