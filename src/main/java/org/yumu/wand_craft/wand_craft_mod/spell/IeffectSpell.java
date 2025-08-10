// IeffectSpell.java
package org.yumu.wand_craft.wand_craft_mod.spell;

/**
 * 效果法术接口
 * 实现此接口的法术可以修改投射物法术的行为
 */
public interface IeffectSpell {
    /**
     * 修改投射物法术
     * @param projectileSpell 投射物法术
     */
    void modifyProjectile(IprojectileSpell projectileSpell);
}
