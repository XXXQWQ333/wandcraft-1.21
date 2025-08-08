package org.yumu.wand_craft.wand_craft_mod.api;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;

public class MagicData {

    public MagicData() {
    }
    public MagicData(ServerPlayer serverPlayer) {

    }

    private ServerPlayer serverPlayer=null;


    private float mana;
    public float getMana() {
        return mana;
    }

    public void setMana(float mana) {
        this.mana = mana;
    }
    public void addMana(float mana) {
        setMana(this.mana+mana);
    }
}
