package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.bomb.BombProjectile;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.magic_missile.MagicArrowProjectile;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.mini_bomb.MiniBombProjectile;

public class EntityRegisry {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, WandCraft.MODID);
    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
    public static final DeferredHolder<EntityType<?>, EntityType<MiniBombProjectile>> MINI_BOMB =
            ENTITIES.register("mini_bomb", () -> EntityType.Builder.<MiniBombProjectile>of(MiniBombProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(WandCraft.MODID, "mini_bomb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BombProjectile>> BOMB =
            ENTITIES.register("bomb", () -> EntityType.Builder.<BombProjectile>of(BombProjectile::new, MobCategory.MISC)
                    .sized(1.0f, 1.0f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(WandCraft.MODID, "bomb").toString()));
    public static final DeferredHolder<EntityType<?>, EntityType<MagicArrowProjectile>> MAGIC_ARROW =
            ENTITIES.register("magic_arrow", () -> EntityType.Builder.<MagicArrowProjectile>of(MagicArrowProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(WandCraft.MODID, "magic_arrow").toString()));
}
