package org.yumu.wand_craft.wand_craft_mod.spell.projectile;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.magic_missile.MagicArrowProjectile;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.mini_bomb.MiniBombProjectile;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractProjectileSpell;

public class MagicArrowSpell extends AbstractProjectileSpell {
    public MagicArrowSpell() {
        super("magic_arrow_spell", 1, 1, false, "magic_arrow_spell");
    }

    @Override
    public void onCast(ItemStack stack, Level level, Player player, InteractionHand hand) {
        this.projectile = new MagicArrowProjectile(level,1.0f,5, player);
        projectile.setSubEffectSpells(this.subEffectSpells);
        Vec3 spawn = player.getEyePosition().add(player.getForward());
        projectile.moveTo(spawn.x, spawn.y - projectile.getBoundingBox().getYsize() / 2, spawn.z, projectile.getYRot() + 180, projectile.getXRot());
        level.addFreshEntity(this.projectile);
    }
}
