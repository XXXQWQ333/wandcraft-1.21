// org/yumu/wand_craft/wand_craft_mod/WandCraft.java
package org.yumu.wand_craft.wand_craft_mod;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.yumu.wand_craft.wand_craft_mod.capability.MagicManager;
import org.yumu.wand_craft.wand_craft_mod.registries.*;

// 这里的值应该与 META-INF/neoforge.mods.toml 文件中的条目匹配
@Mod(WandCraft.MODID)
public class WandCraft {
    // 在一个公共位置定义模组ID，供所有地方引用
    public static final String MODID = "wandcraft";
    // 直接引用slf4j日志记录器
    public static final Logger LOGGER = LogUtils.getLogger();

    public static MagicManager MAGIC_MANAGER;


    // 模组类的构造函数是模组加载时运行的第一段代码。
    // FML将识别一些参数类型如IEventBus或ModContainer并自动传入它们。
    public WandCraft(IEventBus modEventBus, ModContainer modContainer) {

        EventRegistry.setup();
        MAGIC_MANAGER=new MagicManager();
        // 注册commonSetup方法用于模组加载
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(SpellRegistry::registerRegistry);

        // 注册所有内容 - 注意注册顺序
        SpellRegistry.register(modEventBus);
        DataAttachmentRegistry.register(modEventBus);
        BlockRegistry.register(modEventBus);
        ItemRegistry.register(modEventBus);
        CreativeTabRegistry.register(modEventBus);
        AttributeRegistry.register(modEventBus);
        EntityRegisry.register(modEventBus);

        MenuRegistry.register(modEventBus);
        ComponentRegistry.register(modEventBus);

        // 为我们自己注册服务器和其他我们感兴趣的事件。
        // 注意，只有当我们希望这个类(WandCraft)直接响应事件时才需要这行代码。
        // 如果此类中没有用@SubscribeEvent注解的函数(如下方的onServerStarting())，则不要添加这行代码。
        NeoForge.EVENT_BUS.register(this);

        // 注册我们模组的ModConfigSpec，以便FML可以为我们创建和加载配置文件
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // 一些通用设置代码
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // 你可以使用SubscribeEvent让事件总线发现要调用的方法
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // 当服务器启动时做一些事情
        LOGGER.info("HELLO from server starting");
    }

    // 你可以使用EventBusSubscriber自动注册用@SubscribeEvent注解的类中的所有静态方法
    @EventBusSubscriber(modid = WandCraft.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    static class ClientModEvents {
        @SubscribeEvent
        static void onClientSetup(FMLClientSetupEvent event) {
            // 一些客户端设置代码
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
