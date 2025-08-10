// org/yumu/wand_craft/wand_craft_mod/gui/Arcane_Engraver/ArcaneEngraverMenu.java
package org.yumu.wand_craft.wand_craft_mod.gui.Arcane_Engraver;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.yumu.wand_craft.wand_craft_mod.block.Arcane_Engraver.ArcaneEngraverBlock;
import org.yumu.wand_craft.wand_craft_mod.block.Arcane_Engraver.ArcaneEngraverBlockEntity;
import org.yumu.wand_craft.wand_craft_mod.item.SpellCore;
import org.yumu.wand_craft.wand_craft_mod.item.Wand;
import org.yumu.wand_craft.wand_craft_mod.registries.BlockRegistry;
import org.yumu.wand_craft.wand_craft_mod.registries.ItemRegistry;
import org.yumu.wand_craft.wand_craft_mod.registries.MenuRegistry;

/**
 * ArcaneEngraverMenu 是一个用于与奥术雕刻器方块交互的菜单类。
 * 它继承自 AbstractContainerMenu，负责管理玩家与方块实体之间的物品槽位交互。
 * 支持通过网络同步初始化和本地初始化两种方式创建菜单实例。
 */
public class ArcaneEngraverMenu extends AbstractContainerMenu {

    private ArcaneEngraverBlockEntity blockEntity;
    private ContainerLevelAccess access;
    private BlockPos blockPos;

    /**
     * 构造函数：使用方块实体初始化菜单。
     *
     * @param containerId     容器ID，用于标识该菜单实例
     * @param playerInventory 玩家物品栏，用于添加玩家槽位
     * @param blockEntity     奥术雕刻器方块实体，用于添加方块槽位
     */
    public ArcaneEngraverMenu(int containerId, Inventory playerInventory, ArcaneEngraverBlockEntity blockEntity) {
        super(MenuRegistry.ARCANE_ENGRAVER_MENU.get(), containerId);
        this.blockEntity = blockEntity;
        this.blockPos = blockEntity != null ? blockEntity.getBlockPos() : null;
        this.access = blockPos != null && blockEntity != null ?
                ContainerLevelAccess.create(blockEntity.getLevel(), blockPos) :
                ContainerLevelAccess.NULL;

        addPlayerInventorySlots(playerInventory);
        addBlockEntitySlots();
    }

    /**
     * 网络构造函数：从网络缓冲区读取数据初始化菜单。
     * 通常用于客户端初始化时使用。
     *
     * @param containerId     容器ID，用于标识该菜单实例
     * @param playerInventory 玩家物品栏，用于添加玩家槽位
     * @param buf             网络字节缓冲区，包含方块位置等信息
     */
    public ArcaneEngraverMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        super(MenuRegistry.ARCANE_ENGRAVER_MENU.get(), containerId);
        this.blockPos = buf.readBlockPos();
        this.blockEntity = null; // 在客户端初始时为null
        this.access = ContainerLevelAccess.NULL;

        addPlayerInventorySlots(playerInventory);
        // 在网络构造函数中也添加方块实体槽位（使用虚拟容器）
        addDummyBlockEntitySlots();
    }

    /**
     * 添加方块实体中的槽位到菜单中。
     * 包括法杖槽位、法术槽位和一个虚拟的打印按钮槽位。
     */
    private void addBlockEntitySlots() {
        if (blockEntity != null) {
            // 法杖槽位，放在顶部中央位置，更加突出
            this.addSlot(new Slot(blockEntity, 0, 80, 20) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.getItem() instanceof Wand;
                }
            });

            // 法术槽位 (9个槽位)，保持横向排列但位置更合理
            for (int i = 0; i < 9; i++) {
                final int slotIndex = i + 1;
                this.addSlot(new Slot(blockEntity, slotIndex, 8 + i * 18, 48) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        // 只允许放入法术核心物品
                        return stack.getItem() instanceof SpellCore || stack.is(ItemRegistry.SPELL_CORE.get());
                    }
                });
            }

            // 添加打印按钮槽位 (虚拟槽位)，放在底部中央位置
            this.addSlot(new Slot(blockEntity, 10, 80, 76) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false; // 不允许放置物品
                }

                @Override
                public boolean mayPickup(Player player) {
                    return false; // 不允许取出物品
                }

                @Override
                public ItemStack getItem() {
                    return ItemStack.EMPTY; // 返回空物品
                }
            });
        }
    }

    /**
     * 添加虚拟槽位，用于客户端初始化时显示GUI槽位。
     * 使用 SimpleContainer 模拟槽位结构。
     */
    private void addDummyBlockEntitySlots() {
        // 创建一个简单的虚拟容器
        SimpleContainer dummyContainer = new SimpleContainer(11); // 增加一个槽位用于按钮

        // 法杖槽位，放在顶部中央位置
        this.addSlot(new Slot(dummyContainer, 0, 80, 20) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof Wand;
            }
        });

        // 法术槽位 (9个槽位)
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(dummyContainer, i + 1, 8 + i * 18, 48) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    // 只允许放入法术核心物品
                    return stack.getItem() instanceof SpellCore || stack.is(ItemRegistry.SPELL_CORE.get());
                }
            });
        }

        // 添加打印按钮槽位 (虚拟槽位)，放在底部中央位置
        this.addSlot(new Slot(dummyContainer, 10, 80, 76) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public boolean mayPickup(Player player) {
                return false;
            }

            @Override
            public ItemStack getItem() {
                return ItemStack.EMPTY;
            }
        });
    }

    /**
     * 添加玩家物品栏槽位到菜单中。
     * 包括主物品栏和快捷栏。
     *
     * @param playerInventory 玩家物品栏对象
     */
    private void addPlayerInventorySlots(Inventory playerInventory) {
        // 添加玩家物品栏槽位，位置适当调整
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 100 + row * 18));
            }
        }

        // 添加玩家快捷栏槽位
        for (int slot = 0; slot < 9; slot++) {
            this.addSlot(new Slot(playerInventory, slot, 8 + slot * 18, 158));
        }
    }

    /**
     * 处理快速移动物品操作（Shift+点击）。
     * 根据点击的槽位索引决定物品移动方向。
     *
     * @param player 点击的玩家对象
     * @param index  被点击的槽位索引
     * @return 移动后的物品副本
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // 如果点击的是打印按钮槽位，则执行打印操作
        // 由于布局改变，按钮槽位索引现在是46 (法杖1 + 法术槽9 + 按钮1 + 玩家物品栏36 - 1 = 46)
        if (index == 46) { // 打印按钮槽位索引
            if (blockEntity != null && !player.level().isClientSide) {
                printContainerContents(player);
            }
            return ItemStack.EMPTY;
        }

        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotItem = slot.getItem();
            ItemStack itemCopy = slotItem.copy();

            if (index < 36) {
                // 如果是从玩家物品栏移动到方块GUI中
                if (slotItem.getItem() instanceof Wand) {
                    // 尝试放入法杖槽位 (索引36)
                    if (!this.moveItemStackTo(slotItem, 36, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (slotItem.getItem() instanceof SpellCore || slotItem.is(ItemRegistry.SPELL_CORE.get())) {
                    // 尝试放入法术槽位 (索引37-45)
                    if (!this.moveItemStackTo(slotItem, 37, 46, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // 非法杖和非法术核心物品不能放入
                    return ItemStack.EMPTY;
                }
            } else if (index < 46) { // 物品槽位范围 (排除按钮槽位)
                // 如果是从方块GUI移动到玩家物品栏 (索引0-35)
                if (!this.moveItemStackTo(slotItem, 0, 36, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotItem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            return itemCopy;
        }

        return ItemStack.EMPTY;
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
        if (blockPos != null) {
            return ContainerLevelAccess.create(player.level(), blockPos).evaluate(
                    (level, pos) -> level.getBlockState(pos).getBlock() instanceof ArcaneEngraverBlock &&
                            player.distanceToSqr((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D,
                    true);
        }
        return false;
    }

    /**
     * 获取当前菜单关联的方块实体。
     *
     * @return 方块实体对象
     */
    public ArcaneEngraverBlockEntity getBlockEntity() {
        return blockEntity;
    }

    /**
     * 获取当前菜单关联的方块位置。
     *
     * @return 方块位置对象
     */
    public BlockPos getBlockPos() {
        return blockPos;
    }

    /**
     * 打印当前容器内所有槽位的内容到玩家聊天框中。
     * 主要用于调试目的。
     *
     * @param player 当前玩家对象
     */
    private void printContainerContents(Player player) {
        if (blockEntity == null) return;

        // 只向点击按钮的玩家发送消息
        if (player.level().isClientSide) {
            // 发送标题
            player.sendSystemMessage(Component.literal("=== Arcane Engraver Contents ==="));

            // 打印法杖槽位
            ItemStack wandStack = blockEntity.getItem(0);
            Component wandInfo = wandStack.isEmpty() ?
                    Component.literal("Wand slot: Empty") :
                    Component.literal("Wand slot: " + wandStack.getDisplayName().getString() + " x" + wandStack.getCount());
            player.sendSystemMessage(wandInfo);

            // 打印法术槽位（将"Material slots"改为"Spell slots"）
            player.sendSystemMessage(Component.literal("Spell slots:"));

            for (int i = 1; i < 10; i++) {
                ItemStack stack = blockEntity.getItem(i);
                if (!stack.isEmpty()) {
                    Component slotInfo = Component.literal("  Slot " + i + ": " + stack.getDisplayName().getString() + " x" + stack.getCount());
                    player.sendSystemMessage(slotInfo);
                }
            }
        }
    }

    /**
     * 处理菜单按钮点击事件。
     * 当前仅处理索引为46的打印按钮。
     *
     * @param player 点击按钮的玩家
     * @param index  按钮索引
     * @return 如果处理成功返回true，否则返回false
     */
    @Override
    public boolean clickMenuButton(Player player, int index) {
        return false;
    }
}
