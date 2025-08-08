package org.yumu.wand_craft.wand_craft_mod.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.block.Arcane_Engraver.ArcaneEngraverBlock;
import org.yumu.wand_craft.wand_craft_mod.block.Arcane_Engraver.ArcaneEngraverBlockEntity;

public class BlockRegistry {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, WandCraft.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, WandCraft.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final DeferredHolder<Block, Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", ()->new Block(BlockBehaviour.Properties.of()));

    public static final DeferredHolder<Block, ArcaneEngraverBlock> ARCANE_ENGRAVER_BLOCK = BLOCKS.register("arcane_engraver",
            () -> new ArcaneEngraverBlock(BlockBehaviour.Properties.of().strength(3.0F, 3.0F)));
    // Arcane Engraver Block Entity
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArcaneEngraverBlockEntity>> ARCANE_ENGRAVER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("arcane_engraver", () -> BlockEntityType.Builder.of(
                    ArcaneEngraverBlockEntity::new, ARCANE_ENGRAVER_BLOCK.get()).build(null));

}
