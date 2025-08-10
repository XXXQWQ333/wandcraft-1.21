// org/yumu/wand_craft/wand_craft_mod/item/spell/projectile/FireballSpell.java
package org.yumu.wand_craft.wand_craft_mod.spell.projectile;

import net.minecraft.resources.ResourceLocation;
import org.yumu.wand_craft.wand_craft_mod.WandCraft;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.IprojectileSpell;


/**
 * 火球术 - 投射物法术示例
 */
public class FireballSpell extends AbstractSpell implements IprojectileSpell {

    public FireballSpell() {
        super("fireball", 1, 1, false, "fireball");
    }

    @Override
    public boolean onCast() {
        return false;
    }
}
