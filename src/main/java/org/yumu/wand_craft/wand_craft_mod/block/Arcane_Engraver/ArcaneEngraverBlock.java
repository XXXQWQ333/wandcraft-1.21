// 文件: org/yumu/wand_craft/wand_craft_mod/block/Arcane_Engraver/ArcaneEngraverBlock.java
package org.yumu.wand_craft.wand_craft_mod.block.Arcane_Engraver;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.yumu.wand_craft.wand_craft_mod.registries.BlockRegistry;

public class ArcaneEngraverBlock extends BaseEntityBlock {
    public static final MapCodec<ArcaneEngraverBlock> CODEC = simpleCodec(ArcaneEngraverBlock::new);

    public ArcaneEngraverBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ArcaneEngraverBlockEntity(blockPos, blockState);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ArcaneEngraverBlockEntity) {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                serverPlayer.openMenu((ArcaneEngraverBlockEntity) blockEntity, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BlockRegistry.ARCANE_ENGRAVER_BLOCK_ENTITY.get(),
                (level1, blockPos, blockState, blockEntity) -> {
                    if (blockEntity instanceof ArcaneEngraverBlockEntity) {
                        // 可以在这里添加tick逻辑
                    }
                });
    }
}
