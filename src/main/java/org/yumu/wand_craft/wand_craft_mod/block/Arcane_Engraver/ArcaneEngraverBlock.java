// 文件: org/yumu/wand_craft/wand_craft_mod/block/Arcane_Engraver/ArcaneEngraverBlock.java
package org.yumu.wand_craft.wand_craft_mod.block.Arcane_Engraver;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.yumu.wand_craft.wand_craft_mod.gui.Arcane_Engraver.ArcaneEngraverMenu;
import org.yumu.wand_craft.wand_craft_mod.registries.BlockRegistry;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class ArcaneEngraverBlock extends Block {
    public static final MapCodec<ArcaneEngraverBlock> CODEC = simpleCodec((t) -> new ArcaneEngraverBlock());

    public ArcaneEngraverBlock() {
        super(BlockBehaviour.Properties.of().strength(3f).sound(SoundType.STONE));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos), (buf) -> buf.writeBlockPos(pos));
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (i, inventory, player) ->
                        new ArcaneEngraverMenu(i, inventory,
                                ContainerLevelAccess.create(level, pos)),
                                Component.translatable("block.wandcraft.arcane_engraver")

        );
    }


}
