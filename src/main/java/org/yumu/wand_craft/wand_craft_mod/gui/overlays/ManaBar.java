// 文件: org/yumu/wand_craft/wand_craft_mod/gui/overlays/ManaBar.java
package org.yumu.wand_craft.wand_craft_mod.gui.overlays;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;
import org.yumu.wand_craft.wand_craft_mod.registries.AttributeRegistry;

public class ManaBar implements LayeredDraw.Layer {
    // 使用经验条的颜色值
    private static final int MANA_BAR_COLOR = 0x40FFFF; // 青色
    private static final int MANA_BAR_BACKGROUND_COLOR = 0x000000; // 黑色背景
    private static final int MANA_BAR_BORDER_COLOR = 0xFFFFFFFF; // 白色边框

    // Mana条的位置和尺寸
    private static final int BAR_WIDTH = 182;
    private static final int BAR_HEIGHT = 5;

    // 屏幕边距
    private static final int MARGIN = 5;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        // 获取当前玩家
        Player player = net.minecraft.client.Minecraft.getInstance().player;
        if (player == null) return;

        // 获取玩家的魔法数据
        MagicData magicData = MagicData.getPlayerMagicData(player);
        float currentMana = magicData.getMana();

        // 获取玩家的最大魔法值属性
        float maxMana = (float) player.getAttributeValue(AttributeRegistry.MAX_MANA);

        // 处理特殊情况，确保不会除以零
        if (maxMana <= 0) {
            maxMana = 1;
        }

        // 计算mana百分比
        float manaPercent = Math.max(0, Math.min(1, currentMana / maxMana)); // 限制在0-1之间

        // 获取屏幕尺寸
        int screenWidth = net.minecraft.client.Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int screenHeight = net.minecraft.client.Minecraft.getInstance().getWindow().getGuiScaledHeight();

        // 计算mana条的位置（屏幕底部居中）
        int barX = (screenWidth - BAR_WIDTH) / 2;
        int barY = screenHeight - MARGIN - BAR_HEIGHT - 10; // 在默认经验条上方

        // 绘制mana条背景（黑色背景）
        guiGraphics.fill(barX, barY, barX + BAR_WIDTH, barY + BAR_HEIGHT, MANA_BAR_BACKGROUND_COLOR);

        // 绘制mana条前景（根据mana百分比，使用青色填充）
        int filledWidth = (int) (BAR_WIDTH * manaPercent);
        if (filledWidth > 0) {
            guiGraphics.fill(barX, barY, barX + filledWidth, barY + BAR_HEIGHT, MANA_BAR_COLOR);
        }

        // 绘制边框（白色边框）
        guiGraphics.renderOutline(barX, barY, BAR_WIDTH, BAR_HEIGHT, MANA_BAR_BORDER_COLOR);

        // 显示mana数值文本
        String manaText = String.format("%.0f/%.0f", currentMana, maxMana);
        int textWidth = net.minecraft.client.Minecraft.getInstance().font.width(manaText);
        guiGraphics.drawString(
            net.minecraft.client.Minecraft.getInstance().font,
            manaText,
            barX + (BAR_WIDTH - textWidth) / 2,
            barY - 10,
            0x40FFFF, // 青色
            true // 阴影
        );
    }
}
