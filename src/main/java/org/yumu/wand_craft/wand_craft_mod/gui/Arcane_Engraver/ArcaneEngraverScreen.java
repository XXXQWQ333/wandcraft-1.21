// org/yumu/wand_craft/wand_craft_mod/gui/Arcane_Engraver/ArcaneEngraverScreen.java
package org.yumu.wand_craft.wand_craft_mod.gui.Arcane_Engraver;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
/**
 * ArcaneEngraverScreen 是一个用于显示和交互奥术雕刻器GUI的屏幕类。
 * 它继承自 Minecraft 的 AbstractContainerScreen，用于渲染和处理与奥术雕刻器菜单相关的图形界面。
 *
 *  menu 奥术雕刻器的菜单实例，用于管理物品槽和交互逻辑
 *  playerInventory 玩家的物品栏实例
 *  title 屏幕标题文本组件
 */
public class ArcaneEngraverScreen extends AbstractContainerScreen<ArcaneEngraverMenu> {

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath("wandcraft", "textures/gui/arcane_engraver.png");

//    private static final int BUTTON_X = 80;
//    private static final int BUTTON_Y = 76;

    /**
     * 构造函数，初始化屏幕的基本属性。
     *
     * @param menu 奥术雕刻器菜单对象
     * @param playerInventory 玩家物品栏对象
     * @param title 屏幕标题
     */
    public ArcaneEngraverScreen(ArcaneEngraverMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 182;
    }

    /**
     * 渲染背景纹理和按钮文本。
     *
     * @param guiGraphics 用于绘制图形的上下文对象
     * @param partialTick 部分tick时间，用于动画插值
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     */
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    /**
     * 渲染整个屏幕内容，包括背景、控件和提示信息。
     * 当鼠标悬停在“Print”按钮上时，显示提示信息。
     *
     * @param guiGraphics 用于绘制图形的上下文对象
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     * @param partialTick 部分tick时间，用于动画插值
     */
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

//        int guiLeft = (this.width - this.imageWidth) / 2;
//        int guiTop = (this.height - this.imageHeight) / 2;
//
//        // 检查鼠标是否悬停在“Print”按钮区域
//        if (mouseX >= guiLeft + BUTTON_X - 8 && mouseX < guiLeft + BUTTON_X + 10 &&
//            mouseY >= guiTop + BUTTON_Y + 2 && mouseY < guiTop + BUTTON_Y + 18) {
//            guiGraphics.renderTooltip(this.font, Component.literal("Print contents"), mouseX, mouseY);
//        }

        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    /**
     * 处理鼠标点击事件。
     * 如果点击的是“Print”按钮，则调用打印容器内容的方法。
     *
     * @param mouseX 鼠标X坐标
     * @param mouseY 鼠标Y坐标
     * @param button 鼠标按键编号（0为左键，1为右键等）
     * @return 如果事件被处理则返回true，否则返回父类处理结果
     */
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        int guiLeft = (this.width - this.imageWidth) / 2;
//        int guiTop = (this.height - this.imageHeight) / 2;
//
//        // 判断点击是否发生在“Print”按钮区域
//        if (mouseX >= guiLeft + BUTTON_X - 8 && mouseX < guiLeft + BUTTON_X + 10 &&
//            mouseY >= guiTop + BUTTON_Y + 2 && mouseY < guiTop + BUTTON_Y + 18) {
//            printContainerContents();
//            return true;
//        }
//
//        return super.mouseClicked(mouseX, mouseY, button);
//    }

    /**
     * 打印当前容器中所有槽位的内容到聊天栏。
     * 包括主 wand 槽和9个 spell 槽的信息。
     */
    private void printContainerContents() {

        WandCraft.LOGGER.info("isClientSide:"+minecraft.player.level().isClientSide());

        minecraft.player.sendSystemMessage(Component.literal("=== Arcane Engraver Contents ==="));

        ItemStack wandStack = menu.getWandSlot().getItem();
        Component wandInfo = wandStack.isEmpty() ?
                Component.literal("Wand slot: Empty") :
                Component.literal("Wand slot: " + wandStack.getDisplayName().getString() + " x" + wandStack.getCount());
        minecraft.player.sendSystemMessage(wandInfo);

        minecraft.player.sendSystemMessage(Component.literal("Spell slots:"));

        for (int i = 1; i < 10; i++) {
            ItemStack stack = menu.getSpellSlots().get(i).getItem();
            if (!stack.isEmpty()) {
                Component slotInfo = Component.literal("  Slot " + i + ": " + stack.getDisplayName().getString() + " x" + stack.getCount());
                minecraft.player.sendSystemMessage(slotInfo);
            }
        }

    }
}
