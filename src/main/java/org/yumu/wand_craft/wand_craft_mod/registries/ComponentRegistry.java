// org/yumu/wand_craft/wand_craft_mod/registries/ComponentRegistry.java
package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.item.WandData;

import java.util.function.UnaryOperator;

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
}
