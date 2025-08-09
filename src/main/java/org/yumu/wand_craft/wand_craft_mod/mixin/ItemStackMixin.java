package org.yumu.wand_craft.wand_craft_mod.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.function.BiConsumer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.item.Wand;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "forEachModifier(Lnet/minecraft/world/entity/EquipmentSlotGroup;Ljava/util/function/BiConsumer;)V", at = @At("HEAD"))
    private void forEachModifierMixIn(EquipmentSlotGroup slot, BiConsumer<Holder<Attribute>, AttributeModifier> pAction, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;

        // 检查物品是否为法杖且尚未初始化
        if (stack.getItem() instanceof Wand wand) {
//            WandCraft.LOGGER.info("!!!!!!!!!!!!!!!wandcraft$initialized");
            if(true){

            }

        }
    }
}
