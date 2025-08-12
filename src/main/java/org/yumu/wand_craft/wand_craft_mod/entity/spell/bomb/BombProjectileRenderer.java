package org.yumu.wand_craft.wand_craft_mod.entity.spell.bomb;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class BombProjectileRenderer extends EntityRenderer<BombProjectile> {
    private ModelPart bomb;
    public BombProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart root = createBodyLayer().bakeRoot();
        this.bomb = root.getChild("bomb");
    }
    @Override
    public void render(BombProjectile entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        if (entity.tickCount > 1) {
            double dx = entity.getX() - entity.xOld;
            double dy = entity.getY() - entity.yOld;
            double dz = entity.getZ() - entity.zOld;
            float yaw = (float)Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
            float pitch = (float)Math.toDegrees(Math.atan2(dy, Math.sqrt(dx*dx + dz*dz)));
            poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
            poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
        }
        VertexConsumer vertexConsumer = buffer.getBuffer(
                RenderType.entityCutoutNoCull(getTextureLocation(entity))
        );
        bomb.render(poseStack, vertexConsumer, packedLight,
                OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bomb = partdefinition.addOrReplaceChild("bomb", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 8.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    @Override
    protected int getBlockLightLevel(BombProjectile entity, BlockPos pos) {
        return 10;
    }
    @Override
    public ResourceLocation getTextureLocation(BombProjectile entity) {
        return ResourceLocation.fromNamespaceAndPath("wandcraft", "textures/entity/projectile/bomb.png");
    }
}
