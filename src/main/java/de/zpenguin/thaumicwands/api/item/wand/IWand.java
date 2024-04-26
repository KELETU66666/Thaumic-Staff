package de.zpenguin.thaumicwands.api.item.wand;

import de.zpenguin.thaumicwands.api.item.IFractionalVis;
import net.minecraft.item.ItemStack;

public interface IWand extends IFractionalVis {
	public IWandCap getCap(ItemStack stack);
}
