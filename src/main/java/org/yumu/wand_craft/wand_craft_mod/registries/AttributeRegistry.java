package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.attribute.MagicRangedAttribute;

@EventBusSubscriber(modid = WandCraft.MODID, bus = EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, WandCraft.MODID);
    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
    public static final DeferredHolder<Attribute, Attribute> MAX_MANA = ATTRIBUTES.register("max_mana", ()->new MagicRangedAttribute("attribute.wand_craft.max_mana",100.0D,0.0D,1000.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MANA_REGEN_NUM = ATTRIBUTES.register("mana_regen_num", ()->new MagicRangedAttribute("attribute.wand_craft.mana_regen_num",5.0D,0.0D,100.0D).setSyncable(true));

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent e) {
        e.getTypes().forEach(entity -> ATTRIBUTES.getEntries().forEach(attribute -> e.add(entity, attribute)));
    }
}
