package org.yumu.wand_craft.wand_craft_mod.capability;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.yumu.wand_craft.wand_craft_mod.api.MagicData;
import org.yumu.wand_craft.wand_craft_mod.registries.AttributeRegistry;

import static org.yumu.wand_craft.wand_craft_mod.registries.AttributeRegistry.*;

public class MagicManager {
    //法力值恢复周期
    public static int MANA_RECOVERY_TICKS = 10;

    public boolean regenPlayerMana(ServerPlayer  serverPlayer,MagicData playerMagicData){
        float playerMaxMana=(float)serverPlayer.getAttributeValue(MAX_MANA);
        float mana = playerMagicData.getMana();
        if(mana!=playerMaxMana){
            float playerManaRegenNum=(float)serverPlayer.getAttributeValue(MANA_REGEN_NUM);
            playerMagicData.setMana(Mth.clamp(playerMagicData.getMana()+playerManaRegenNum,0,playerMaxMana));
            return true;
        }else {
            return false;
        }
    }
    public void tick(Level level){
        boolean shouldManaRegen=level.getServer().getTickCount()%MANA_RECOVERY_TICKS==0;
        level.players().stream().toList().forEach(player -> {
            if(player instanceof ServerPlayer serverPlayer){
                MagicData playerMagicData = MagicData.getPlayerMagicData(serverPlayer);
                /*
                * 每一个游戏刻的逻辑
                * */
                if(shouldManaRegen){//法力值恢复
                    if(regenPlayerMana(serverPlayer,playerMagicData)){

                    }
                }
            }
        });
    }

}
