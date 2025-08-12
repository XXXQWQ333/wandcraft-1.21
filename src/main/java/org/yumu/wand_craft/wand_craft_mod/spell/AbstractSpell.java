// org/yumu/wand_craft/wand_craft_mod/item/spell/AbstractSpell.java
package org.yumu.wand_craft.wand_craft_mod.spell;

import net.minecraft.resources.ResourceLocation;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;

/**
 * 法术类，用于表示和处理游戏中的法术信息
 * 包含法术名称以及相应的编解码功能
 * 法术的分类应该分为：
 * 投射物类：
 *  类似与箭矢，火球之类的发射物
 * 效果类：
 *  改变法术的效果，比如延长时间，分裂，多重施法等
 */
public abstract class AbstractSpell {

    /**法术消耗的施法次数
     * 可以是正负数都可以
     * */
    protected int costCastCount;
    /**法术的消耗魔力*/
    protected int costMana;

    /**是否为区分两个法术块的标志法术*/
    private boolean isBlockPoint;
    private ResourceLocation spellId;
    private String spellName;
    //protected ResourceLocation nextSpellId=null;

    /**
     * 构造一个新的法术对象
     * @param spellId 法术的名称
     */
    public AbstractSpell(String spellId, int costCastCount, int costMana, boolean isBlockPoint, String spellName) {
        // 将 spellId 转换为小写以符合 ResourceLocation 的要求
        this.spellId = ResourceLocation.fromNamespaceAndPath(WandCraft.MODID, spellId.toLowerCase());
        this.costCastCount = costCastCount;
        this.costMana = costMana;
        this.isBlockPoint = isBlockPoint;
        this.spellName = spellName;

    }

    public AbstractSpell Copy() {
        try {
            // 使用反射创建新实例（需要每个子类有无参构造函数）
            AbstractSpell copy = this.getClass().getDeclaredConstructor().newInstance();
            copy.costCastCount = this.costCastCount;
            copy.costMana = this.costMana;
            copy.isBlockPoint = this.isBlockPoint;
            copy.spellId = this.spellId;
            copy.spellName = this.spellName;
            // nextSpellId 保持为 null
            return copy;
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy spell", e);
        }
    }
//    public abstract AbstractSpell Copy();

    /**
     * 获取法术名称
     * @return 返回法术的名称字符串
     */

    public String getSpellName() {
        return spellName;
    }

    public ResourceLocation getSpellId() {
        return spellId;
    }

    public int getCostCastCount() {
        return costCastCount;
    }

    public int getCostMana() {
        return costMana;
    }

//    public void setNextSpellId(ResourceLocation spellId) {
//        this.nextSpellId = spellId;
//    }
//    public AbstractSpell getNextSpellId() {
//        return nextSpell;
//    }

    public boolean isBlockPoint() {
        return isBlockPoint;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AbstractSpell that = (AbstractSpell) obj;

        return spellId != null ? spellId.equals(that.spellId) : that.spellId == null;
    }

    @Override
    public int hashCode() {
        return spellId != null ? spellId.hashCode() : 0;
    }

    public ResourceLocation getSpellResource(){
        return spellId;
    }

}
