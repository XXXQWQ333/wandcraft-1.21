package org.yumu.wand_craft.wand_craft_mod.spell.effect;

import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.IeffectSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.IprojectileSpell;

/**
 * 速度法术 - 效果法术示例
 */
public class SpeedEnhancementSpell extends AbstractSpell implements IeffectSpell {

    public SpeedEnhancementSpell() {
        super("speed_enhancement", 0, 1, false, "speed_enhancement");
    }

    @Override
    public void modifyProjectile(IprojectileSpell projectileSpell) {
        //投射物法术修正效果
    }
}
