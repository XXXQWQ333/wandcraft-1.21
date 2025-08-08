package org.yumu.wand_craft.wand_craft_mod.attribute;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

/**
 * 魔法范围属性类
 * 继承自RangedAttribute，用于定义魔法相关的数值属性范围
 */
public class MagicRangedAttribute extends RangedAttribute {
    /**
     * 构造函数，创建一个新的魔法范围属性
     *
     * @param descriptionId 属性的描述ID，用于本地化显示
     * @param defaultValue 属性的默认值
     * @param min 属性的最小值
     * @param max 属性的最大值
     */
    public MagicRangedAttribute(String descriptionId, double defaultValue, double min, double max) {
        super(descriptionId, defaultValue, min, max);
    }
}

