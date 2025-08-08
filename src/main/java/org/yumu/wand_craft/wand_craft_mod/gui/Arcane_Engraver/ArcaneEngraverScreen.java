// org/yumu/wand_craft/wand_craft_mod/gui/Arcane_Engraver/ArcaneEngraverScreen.java
package org.yumu.wand_craft.wand_craft_mod.gui.Arcane_Engraver;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

/**
 * 神秘雕刻器GUI屏幕类，用于渲染和处理与雕刻器菜单的交互。
 * 该类继承自AbstractContainerScreen，提供图形界面的绘制、鼠标点击处理和提示信息显示功能。
 */
public class ArcaneEngraverScreen extends AbstractContainerScreen<ArcaneEngraverMenu> {
    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath("wandcraft", "textures/gui/arcane_engraver.png");

    // 在类中添加按钮位置常量 (现在按钮在(80, 76)位置)
    private static final int BUTTON_X = 80;
    private static final int BUTTON_Y = 76;

    /**
     * 构造函数，初始化神秘雕刻器屏幕的基本属性。
     *
     * @param menu           与屏幕关联的菜单对象
     * @param playerInventory 玩家物品栏对象
     * @param title          屏幕标题组件
     */
    public ArcaneEngraverScreen(ArcaneEngraverMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 182; // 调整高度以适应新布局
    }

    /**
     * 渲染背景纹理和按钮文本。
     *
     * @param guiGraphics GUI图形绘制上下文
     * @param partialTick 部分tick时间（用于动画插值）
     * @param mouseX      鼠标X坐标
     * @param mouseY      鼠标Y坐标
     */
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // 绘制打印按钮文本，位置调整以适应新布局
        guiGraphics.drawString(this.font, "Print", x + BUTTON_X - 10, y + BUTTON_Y + 6, 0xFFFFFF);
    }

    /**
     * 渲染整个屏幕内容，包括背景、控件和悬停提示。
     *
     * @param guiGraphics GUI图形绘制上下文
     * @param mouseX      鼠标X坐标
     * @param mouseY      鼠标Y坐标
     * @param partialTick 部分tick时间（用于动画插值）
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // 渲染按钮悬停提示 (更新检测区域以匹配新按钮位置)
        int guiLeft = (this.width - this.imageWidth) / 2;
        int guiTop = (this.height - this.imageHeight) / 2;

        // 更新按钮检测区域，使其更精确地匹配按钮位置
        if (mouseX >= guiLeft + BUTTON_X - 8 && mouseX < guiLeft + BUTTON_X + 10 &&
            mouseY >= guiTop + BUTTON_Y + 2 && mouseY < guiTop + BUTTON_Y + 18) {
            guiGraphics.renderTooltip(this.font, Component.literal("Print contents"), mouseX, mouseY);
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    /**
     * 处理鼠标点击事件，检测是否点击了打印按钮。
     *
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     * @param button 被点击的鼠标按键编号
     * @return 如果事件被处理则返回true，否则返回false
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int guiLeft = (this.width - this.imageWidth) / 2;
        int guiTop = (this.height - this.imageHeight) / 2;

        // 检查是否点击了打印按钮 (更新检测区域以匹配新按钮位置)
        if (mouseX >= guiLeft + BUTTON_X - 8 && mouseX < guiLeft + BUTTON_X + 10 &&
            mouseY >= guiTop + BUTTON_Y + 2 && mouseY < guiTop + BUTTON_Y + 18) {
            // 在客户端直接打印容器内容
            printContainerContents();
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    /**
     * 在客户端打印容器内容
     */
    private void printContainerContents() {
        // 发送标题
        minecraft.player.sendSystemMessage(Component.literal("=== Arcane Engraver Contents ==="));

        // 打印法杖槽位 (索引0)
        ItemStack wandStack = menu.getSlot(0).getItem();
        Component wandInfo = wandStack.isEmpty() ?
                Component.literal("Wand slot: Empty") :
                Component.literal("Wand slot: " + wandStack.getDisplayName().getString() + " x" + wandStack.getCount());
        minecraft.player.sendSystemMessage(wandInfo);

        // 打印法术槽位 (索引1-9)
        minecraft.player.sendSystemMessage(Component.literal("Spell slots:"));

        for (int i = 1; i < 10; i++) {
            ItemStack stack = menu.getSlot(i).getItem();
            if (!stack.isEmpty()) {
                Component slotInfo = Component.literal("  Slot " + i + ": " + stack.getDisplayName().getString() + " x" + stack.getCount());
                minecraft.player.sendSystemMessage(slotInfo);
            }
        }
    }
}
