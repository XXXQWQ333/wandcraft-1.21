package org.yumu.wand_craft.wand_craft_mod.spell;

public class NoneSpell extends AbstractSpell implements IprojectileSpell,IeffectSpell{

    @Override
    public void modifyProjectile(IprojectileSpell projectileSpell) {

    }

    @Override
    public boolean onCast() {
        return false;
    }

    public NoneSpell() {
        super("none", 0, 0, false, "none");
    }

}
