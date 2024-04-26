package de.zpenguin.thaumicwands.api.item.wand;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;

public interface IStaffCore {

	public ResourceLocation getTexture();

	public ItemStack getItemStack();

	public String getTag();

	public String getRequiredResearch();

	public int getCapacity();

	public default boolean hasUpdate() {
		return getUpdate() !=null;
	}

	public IWandUpdate getUpdate();

}
