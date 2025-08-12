package org.yumu.wand_craft.wand_craft_mod.entity.spell.mini_bomb;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


/**
 * 小型炸弹投射物渲染器
 * 负责渲染MiniBombProjectile实体
 */
@OnlyIn(Dist.CLIENT)
public class MiniBombProjectileRenderer extends EntityRenderer<MiniBombProjectile> {


    /**
     * 构造函数
     *
     * @param context 实体渲染器提供者上下文
     */
    public MiniBombProjectileRenderer(Context context) {
        super(context);}

    @Override
    public void render(MiniBombProjectile entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
        // 在这里添加自定义渲染逻辑
        // 当前实现使用简单的方块纹理渲染
    }

    @Override
    protected int getBlockLightLevel(MiniBombProjectile entity, BlockPos pos) {
        // 返回最大亮度，确保实体始终明亮可见
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(MiniBombProjectile entity) {
        // 使用方块图集纹理，不需要单独的纹理文件
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
