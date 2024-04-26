package de.zpenguin.thaumicwands.api.item.wand;

import net.minecraft.item.ItemStack;

public interface IStaff extends IWand{
    public IStaffCore getCore(ItemStack stack);
}
