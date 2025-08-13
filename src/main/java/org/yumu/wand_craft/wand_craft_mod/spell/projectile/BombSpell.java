package org.yumu.wand_craft.wand_craft_mod.spell.projectile;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.yumu.wand_craft.wand_craft_mod.entity.spell.bomb.BombProjectile;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractProjectileSpell;
import org.yumu.wand_craft.wand_craft_mod.spell.AbstractSpell;

public class BombSpell extends AbstractProjectileSpell {
    public BombSpell() {
        super("bomb", 1, 1, false, "bomb");
    }

    @Override
    public void onCast(ItemStack stack, Level level, Player player, InteractionHand hand) {
        this.projectile = new BombProjectile(level,0.6f,10, player);
        projectile.setSubEffectSpells(this.subEffectSpells);
        Vec3 spawn = player.getEyePosition().add(player.getForward());
        projectile.moveTo(spawn.x, spawn.y - projectile.getBoundingBox().getYsize() / 2, spawn.z, projectile.getYRot() + 180, projectile.getXRot());
        level.addFreshEntity(this.projectile);
    }

    @Override
    public AbstractSpell Copy() {
        return new BombSpell();
    }
}
