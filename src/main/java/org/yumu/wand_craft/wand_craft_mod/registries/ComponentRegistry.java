// org/yumu/wand_craft/wand_craft_mod/registries/ComponentRegistry.java
package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.api.SpellData;
import org.yumu.wand_craft.wand_craft_mod.api.WandData;

import java.util.function.UnaryOperator;

/**
 * 注册的是数据组件(Data Components)，这些组件附加到物品(ItemStack)上，
 * 用于存储物品相关的数据。这些数据是物品本身的一部分，会随着物品的移动而移动
 */
public class ComponentRegistry {
    private static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, WandCraft.MODID);

    public static void register(IEventBus eventBus) {
        COMPONENTS.register(eventBus);
    }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String pName, UnaryOperator<DataComponentType.Builder<T>> pBuilder) {
        return COMPONENTS.register(pName, () -> pBuilder.apply(DataComponentType.builder()).build());
    }

    // 确保这里正确引用了STREAM_CODEC
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<WandData>> WAND_COMPONENT = register("wand_data",
        (builder) -> builder.persistent(WandData.CODEC).networkSynchronized(WandData.STREAM_CODEC).cacheEncoding());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SpellData>> SPELL_COMPONENT = register("spell_data",
            (builder) -> builder.persistent(SpellData.CODEC).networkSynchronized(SpellData.STREAM_CODEC).cacheEncoding());

}
