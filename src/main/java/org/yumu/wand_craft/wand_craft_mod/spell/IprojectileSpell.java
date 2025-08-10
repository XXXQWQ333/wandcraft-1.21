package org.yumu.wand_craft.wand_craft_mod.spell;

//在如今架构上帮我设计一个架构，使得： 具体的法术类需要继承AbstractSpell和其中的一个接口。 释放的投射类法术要收到效果影响类法术的影响，具体的影响在具体影响类法术内定义

/**
 * 投射物法术接口
 * 实现此接口的法术可以被效果法术影响
 */
public interface IprojectileSpell {
    public boolean onCast();
}