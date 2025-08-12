// org/yumu/wand_craft/wand_craft_mod/item/spell/projectile/FireballSpell.java
package org.yumu.wand_craft.wand_craft_mod.spell.projectile;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.AbstractMagicProjectile;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractProjectileSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;


/**
 * 火球术 - 投射物法术示例
 */
public class FireballSpell extends AbstractProjectileSpell {

    public FireballSpell() {
        super("fireball", 1, 1, false, "fireball");
    }

    @Override
    public void onCast(ItemStack stack, Level level, Player player, InteractionHand hand) {

    }


    @Override
    public AbstractSpell Copy() {
        return new FireballSpell();
    }
}
