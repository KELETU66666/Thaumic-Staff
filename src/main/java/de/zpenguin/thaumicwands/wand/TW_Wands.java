package de.zpenguin.thaumicwands.wand;

import java.util.ArrayList;

import de.zpenguin.thaumicwands.api.ThaumicWandsAPI;
import de.zpenguin.thaumicwands.api.item.wand.IStaffCore;
import de.zpenguin.thaumicwands.api.item.wand.IWandCap;
import de.zpenguin.thaumicwands.api.item.wand.IWandRod;
import de.zpenguin.thaumicwands.item.TW_Items;
import de.zpenguin.thaumicwands.wand.updates.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class TW_Wands {

	public static final ArrayList<IWandCap> CAPS = new ArrayList<IWandCap>();
	public static final ArrayList<IWandRod> RODS = new ArrayList<IWandRod>();
	public static final ArrayList<IStaffCore> STAFF = new ArrayList<IStaffCore>();

	public static final IWandCap capIron = new WandCap("iron", 1F, new ItemStack(TW_Items.itemWandCap, 1, 0), 1, "UNLOCKTHAUMATURGY");
	public static final IWandCap capCopper = new WandCap("copper", 1F, new AspectList().add(Aspect.ORDER,1).add(Aspect.ENTROPY,1),new ItemStack(TW_Items.itemWandCap, 1, 1), 5);
	public static final IWandCap capBrass = new WandCap("brass", 0.95F, new ItemStack(TW_Items.itemWandCap, 1, 2), 10);
	public static final IWandCap capSilver = new WandCap("silver", 0.95F, new AspectList().add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1),new ItemStack(TW_Items.itemWandCap, 1, 4), 15);
	public static final IWandCap capThaumium = new WandCap("thaumium", 0.9F, new ItemStack(TW_Items.itemWandCap, 1, 6), 20);
	public static final IWandCap capVoid = new WandCap("void", 0.8F, new ItemStack(TW_Items.itemWandCap,1,8), 25);

	public static final IWandRod rodWood = new WandRod("wood", 25, new ItemStack(Items.STICK), 1, "UNLOCKTHAUMATURGY");
	public static final IWandRod rodGreatwood = new WandRod("greatwood", 100, new ItemStack(TW_Items.itemWandRod, 1, 0), 5);
	public static final IWandRod rodReed = new WandRod("reed", 300, new ItemStack(TW_Items.itemWandRod, 1, 1), 10, new UpdateAir());
	public static final IWandRod rodBlaze = new WandRod("blaze", 300, new ItemStack(TW_Items.itemWandRod, 1, 2), 10, new UpdateFire());
	public static final IWandRod rodIce = new WandRod("ice", 300, new ItemStack(TW_Items.itemWandRod, 1, 3), 10, new UpdateWater());
	public static final IWandRod rodObsidian = new WandRod("obsidian", 300, new ItemStack(TW_Items.itemWandRod, 1, 4), 10, new UpdateEarth());
	public static final IWandRod rodQuartz = new WandRod("quartz", 300, new ItemStack(TW_Items.itemWandRod, 1, 5), 10);
	public static final IWandRod rodBone = new WandRod("bone", 300, new ItemStack(TW_Items.itemWandRod, 1, 6), 10);
	public static final IWandRod rodSilverwood = new WandRod("silverwood", 400, new ItemStack(TW_Items.itemWandRod, 1, 7), 15);
	public static final IStaffCore coreGreatwood = new StaffCore("greatwood", (int) (200 * 2.5), new ItemStack(TW_Items.itemStaffCore, 1, 0));
	public static final IStaffCore coreReed = new StaffCore("reed", (int) (600 * 2.5), new ItemStack(TW_Items.itemStaffCore, 1, 1), new UpdateAir());
	public static final IStaffCore coreBlaze = new StaffCore("blaze", (int) (600 * 2.5), new ItemStack(TW_Items.itemStaffCore, 1, 2), new UpdateFire());
	public static final IStaffCore coreIce = new StaffCore("ice", (int) (600 * 2.5), new ItemStack(TW_Items.itemStaffCore, 1, 3), new UpdateWater());
	public static final IStaffCore coreObsidian = new StaffCore("obsidian", (int) (600 * 2.5), new ItemStack(TW_Items.itemStaffCore, 1, 4), new UpdateEarth());
	public static final IStaffCore coreQuartz = new StaffCore("quartz", (int) (600 * 2.5), new ItemStack(TW_Items.itemStaffCore, 1, 5));
	public static final IStaffCore coreBone = new StaffCore("bone", (int) (600 * 2.5), new ItemStack(TW_Items.itemStaffCore, 1, 6));
	public static final IStaffCore coreSilverwood = new StaffCore("silverwood", (int) (800 * 2.5), new ItemStack(TW_Items.itemStaffCore, 1, 7));
	public static final IStaffCore corePrimal = new StaffCore("primal", 375, new ItemStack(TW_Items.itemStaffCore, 1, 8), new UpdatePrimal());

	public static void registerWandParts() {
		for(IWandCap cap: CAPS)
			ThaumicWandsAPI.registerWandCap(cap.getTag(), cap);
		for(IWandRod rod: RODS)
			ThaumicWandsAPI.registerWandRod(rod.getTag(), rod);
		for(IStaffCore core: STAFF)
			ThaumicWandsAPI.registerStaffCore(core.getTag(), core);
	}

}
