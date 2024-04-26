package de.zpenguin.thaumicwands.api.item.wand;

import net.minecraft.item.ItemStack;
import thaumcraft.api.casters.ICaster;
import thaumcraft.api.items.IArchitect;

public interface IWandBasic extends IArchitect, ICaster, IWand{
    public IWandRod getRod(ItemStack stack);
}
