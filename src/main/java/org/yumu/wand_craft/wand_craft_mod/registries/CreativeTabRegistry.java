// org/yumu/wand_craft/wand_craft_mod/registries/CreativeTabRegistry.java
package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.api.SpellData;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;

import java.util.List;

@EventBusSubscriber(modid = WandCraft.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CreativeTabRegistry {

    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WandCraft.MODID);

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.wandcraft"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ItemRegistry.EXAMPLE_ITEM.get().getDefaultInstance())
                    .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SPELL_CORE_TAB = CREATIVE_MODE_TABS.register("spell_core_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.wandcraft.spell_cores"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> new ItemStack(ItemRegistry.SPELL_CORE.get()))
                    .build());

    // 处理创造模式标签页内容构建事件
    @SubscribeEvent
    public static void fillCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == EXAMPLE_TAB.get()) {
            // 添加物品到主创造物品栏
            event.accept(ItemRegistry.EXAMPLE_ITEM.get());
            event.accept(ItemRegistry.EXAMPLE_BLOCK_ITEM.get());
            event.accept(ItemRegistry.ARCANE_ENGRAVER_BLOCK_ITEM.get());

        } else if (event.getTab() == SPELL_CORE_TAB.get()) {
            // 为每个已注册的法术添加法术核心
            event.accept(ItemRegistry.WAND.get());
            try {
                // 检查注册表是否已初始化
                SpellRegistry.getSpells().stream().forEach(spell -> {
                    ItemStack stack = new ItemStack(ItemRegistry.SPELL_CORE.get());
                        stack.set(ComponentRegistry.SPELL_COMPONENT.get(), new SpellData(spell));
                        event.accept(stack);
                });

//                if (SpellRegistry.getRegistry() != null) {
//                    // 遍历所有已注册的法术
//                    SpellRegistry.getRegistry().holders().forEach(spellHolder -> {
//                        AbstractSpell spell = spellHolder.value();
//                        ItemStack stack = new ItemStack(ItemRegistry.SPELL_CORE.get());
//                        stack.set(ComponentRegistry.SPELL_COMPONENT.get(), new SpellData(spell));
//                        event.accept(stack);
//                    });
//                }
            } catch (Exception e) {
                // 如果出现异常，记录错误但不中断其他物品的添加
                WandCraft.LOGGER.error("Error adding spell cores to creative tab", e);
            }
        }
    }
}
