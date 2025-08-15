// org/yumu/wand_craft/wand_craft_mod/gui/Arcane_Engraver/ArcaneEngraverMenu.java
package org.yumu.wand_craft.wand_craft_mod.gui.Arcane_Engraver;


import net.minecraft.network.FriendlyByteBuf;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.yumu.wand_craft.wand_craft_mod.api.*;

import org.yumu.wand_craft.wand_craft_mod.item.SpellCore;
import org.yumu.wand_craft.wand_craft_mod.item.Wand;

import org.yumu.wand_craft.wand_craft_mod.registries.*;

import java.util.ArrayList;
import java.util.List;


/**
 * ArcaneEngraverMenu 是一个用于与奥术雕刻器方块交互的菜单类。
 * 它继承自 AbstractContainerMenu，负责管理玩家与方块实体之间的物品槽位交互。
 *
 */
public class ArcaneEngraverMenu extends AbstractContainerMenu {

    private final Player player;
    private final Level level;
    private final Slot wandSlot;
    private final ArrayList<Slot> spellSlots;
    protected final ContainerLevelAccess access;
    protected final Container spellContainer=new SimpleContainer(9){
        @Override
        public void setChanged() {
            super.setChanged();
            ArcaneEngraverMenu.this.slotsChanged(this);
        }
    };
    protected final Container wandContainer=new SimpleContainer(1){
        @Override
        public void setChanged() {
            super.setChanged();
            ArcaneEngraverMenu.this.slotsChanged(this);
        }

        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return super.canPlaceItem(slot, stack);
        }
    };
    
    public ArcaneEngraverMenu(int containerId, Inventory inv,FriendlyByteBuf extraData ) {
        this(containerId, inv, ContainerLevelAccess.NULL);
    }

    public ArcaneEngraverMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(MenuRegistry.ARCANE_ENGRAVER_MENU.get(), containerId);
        this.access = access;
        checkContainerSize(playerInventory, 10);
        this.level = playerInventory.player.level();
        this.player = playerInventory.player;

        // 添加玩家物品栏
        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        // 创建法杖槽位 (位于GUI中心上方)
        this.wandSlot = new Slot(wandContainer, 0, 80, 17) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return (stack.getItem() instanceof Wand && Wand.isInitialized(stack));
            }

            @Override
            public void set(ItemStack stack) {
                super.set(stack);
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
            }
        };

        this.addSlot(wandSlot);

        // 创建法术槽位 (位于GUI中心区域)
        spellSlots = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            final int slotIndex = i; // 添加这一行来捕获当前索引
            spellSlots.add(new Slot(spellContainer, i, 8 + i * 18, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    // 只有当法杖槽位有法杖时才允许放入法术核心
                    ItemStack wandStack = wandContainer.getItem(0);
                    if (!(stack.getItem() instanceof SpellCore &&
                            !wandStack.isEmpty() &&
                            wandStack.getItem() instanceof Wand &&
                            Wand.isInitialized(wandStack))) {
                        return false;
                    }

                    // 检查当前槽位索引是否小于法杖的最大法术槽位数
                    WandData wandData = wandStack.get(ComponentRegistry.WAND_COMPONENT.get());
                    if (wandData != null && wandData.isInitialized()) {
                        return slotIndex < wandData.getMaxSpellSlot();
                    }

                    return false;
                }

                @Override
                public void set(ItemStack stack) {
                    super.set(stack);
                }

                @Override
                public void onTake(Player player, ItemStack stack) {
                    super.onTake(player, stack);
                }
            });
            this.addSlot(spellSlots.get(i));
        }




    }

    public ArrayList<Slot> getSpellSlots() {
        return spellSlots;
    }

    public Slot getWandSlot() {
        return wandSlot;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == wandContainer) {
            ItemStack wandStack = wandContainer.getItem(0);
            if (!wandStack.isEmpty() && wandStack.getItem() instanceof Wand) {
                updateSpellSlotsFromWand(wandStack);
            } else {
                // 清空法术槽位
                for (int i = 0; i < 9; i++) {
                    spellContainer.setItem(i, ItemStack.EMPTY);
                }
            }
        } else if (container == spellContainer) {
            ItemStack wandStack = wandContainer.getItem(0);
            if (!wandStack.isEmpty() && wandStack.getItem() instanceof Wand) {
                saveSpellsToWand(wandStack);
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack slotItem = slot.getItem();
        ItemStack itemCopy = slotItem.copy();

        // 玩家物品栏槽位索引范围: 0-35
        if (index >= 0 && index < 36) {
            // 从玩家物品栏移动到方块GUI中
            if (slotItem.getItem() instanceof Wand && Wand.isInitialized(slotItem)) {
                // 尝试放入法杖槽位 (索引36)
                if (!this.moveItemStackTo(slotItem, 36, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotItem.getItem() instanceof SpellCore) {
                // 尝试放入法术槽位 (索引37-45)
                if (!this.moveItemStackTo(slotItem, 37, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // 不支持的物品类型
                return ItemStack.EMPTY;
            }
        }
        // GUI槽位索引范围: 36-45 (法杖槽36, 法术槽37-45)
        else if (index >= 36 && index < 46) {
            // 从方块GUI移动到玩家物品栏 (索引0-35)
            if (!this.moveItemStackTo(slotItem, 0, 36, true)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }

        if (slotItem.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return itemCopy;
    }




    /**
     * 判断当前玩家是否仍然可以访问该菜单。
     * 检查玩家与方块的距离以及方块是否仍为奥术雕刻器。
     *
     * @param player 当前玩家对象
     * @return 如果玩家仍可访问返回true，否则返回false
     */
    @Override
    public boolean stillValid(Player player) {
        return this.access.evaluate((level, blockPos) -> {
            return !level.getBlockState(blockPos).is(BlockRegistry.ARCANE_ENGRAVER_BLOCK.get()) ?
                    false : player.distanceToSqr((double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D) <= 64.0D;
        }, true);
    }

    /**
     * 当容器关闭时调用，将客户端容器中的物品返回给玩家
     */
    @Override
    public void removed(Player player) {
        super.removed(player);
        if (player instanceof ServerPlayer) {
            super.removed(player);
            this.access.execute((p_39796_, p_39797_) -> {
//                this.clearContainer(player, this.spellContainer);
                this.clearContainer(player, this.wandContainer);
            });
        }
    }
    /**
     * 根据法杖数据更新法术槽位显示
     * @param wandStack 法杖物品栈
     */
    private void updateSpellSlotsFromWand(ItemStack wandStack) {
        WandData wandData = wandStack.get(ComponentRegistry.WAND_COMPONENT.get());
        if (wandData != null && wandData.isInitialized()) {
            List<ResourceLocation> spellIds = wandData.getSpellIds();
            for (int i = 0; i < 9; i++) {
                if (i < spellIds.size() && spellIds.get(i) != null&&!spellIds.get(i).equals(SpellRegistry.NONE.getId())) {
                    // 根据spellId获取对应的SpellCore物品
                    ItemStack spellStack = new ItemStack(ItemRegistry.SPELL_CORE.get());
                    SpellData spellData = new SpellData(spellIds.get(i));
                    spellStack.set(ComponentRegistry.SPELL_COMPONENT.get(), spellData);
                    spellContainer.setItem(i, spellStack);
                } else {
                    spellContainer.setItem(i, ItemStack.EMPTY);
                }
//                spellContainer.setItem(i, ItemStack.EMPTY);
            }
        }
    }
    /**
     * 将法术槽位的内容保存到法杖中
     * @param wandStack 法杖物品栈
     */
    public void saveSpellsToWand(ItemStack wandStack) {
        WandData wandData = wandStack.get(ComponentRegistry.WAND_COMPONENT.get());
        if (wandData != null && wandData.isInitialized()) {
            List<ResourceLocation> spellIds = new ArrayList<>();

            for (int i = 0; i < 9; i++) {
                ItemStack spellStack = spellContainer.getItem(i);
                // 检查法术槽位是否为法术核心物品且不为空
                if (!spellStack.isEmpty() && spellStack.getItem() instanceof SpellCore) {
                    SpellData spellData = spellStack.get(ComponentRegistry.SPELL_COMPONENT.get());
                    // 检查法术数据是否不为空
                    if (spellData != null && spellData.getSpell() != null) {
                        spellIds.add(SpellRegistry.getSpellId(spellData.getSpell().getSpellName()));
                    } else {
                        spellIds.add(SpellRegistry.NONE.getId());
                    }
                } else {
                    spellIds.add(SpellRegistry.NONE.getId());
                }
            }
            wandData.setSpellIds(spellIds);
            wandData.setIndex(0);

//            wandStack.set(ComponentRegistry.WAND_COMPONENT.get(), newWandData);
        }
    }
    private void addPlayerInventory(Inventory playerInventory) {
        // 玩家物品栏 (3行9列)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 95 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        // 玩家快捷栏 (1行9列)
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 153));
        }
    }
}
