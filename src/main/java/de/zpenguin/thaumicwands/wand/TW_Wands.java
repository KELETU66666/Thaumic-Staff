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

public class TW_Wands {

	public static final ArrayList<IWandCap> CAPS = new ArrayList<IWandCap>();
	public static final ArrayList<IWandRod> RODS = new ArrayList<IWandRod>();
	public static final ArrayList<IStaffCore> STAFF = new ArrayList<IStaffCore>();

	public static final IWandCap capIron = new WandCap("iron", 1.1F, new ItemStack(TW_Items.itemWandCap, 1, 0), 1, "UNLOCKTHAUMATURGY");
	//public static final IWandCap capCopper = new WandCap("copper", 1F, new AspectList().add(Aspect.ORDER,1).add(Aspect.ENTROPY,1),new ItemStack(TW_Items.itemWandCap, 1, 1), 5);
	public static final IWandCap capGold = new WandCap("gold", 1.0F, new ItemStack(TW_Items.itemWandCap, 1, 1), 10);
	public static final IWandCap capBrass = new WandCap("brass", 1.0F, new ItemStack(TW_Items.itemWandCap, 1, 2), 15);
	//public static final IWandCap capSilver = new WandCap("silver", 0.95F, new AspectList().add(Aspect.AIR,1).add(Aspect.FIRE,1).add(Aspect.WATER,1).add(Aspect.EARTH,1),new ItemStack(TW_Items.itemWandCap, 1, 4), 15);
	public static final IWandCap capThaumium = new WandCap("thaumium", 0.9F, new ItemStack(TW_Items.itemWandCap, 1, 4), 20);
	public static final IWandCap capVoid = new WandCap("void", 0.8F, new ItemStack(TW_Items.itemWandCap,1,6), 25);

	public static final IWandRod rodWood = new WandRod("wood", 50, new ItemStack(Items.STICK), 1, "UNLOCKTHAUMATURGY");
	public static final IWandRod rodGreatwood = new WandRod("greatwood", 100, new ItemStack(TW_Items.itemWandRod, 1, 0), 5);
	public static final IWandRod rodReed = new WandRod("reed", 200, new ItemStack(TW_Items.itemWandRod, 1, 1), 10, new UpdateAir());
	public static final IWandRod rodBlaze = new WandRod("blaze", 200, new ItemStack(TW_Items.itemWandRod, 1, 2), 10, new UpdateFire());
	public static final IWandRod rodIce = new WandRod("ice", 200, new ItemStack(TW_Items.itemWandRod, 1, 3), 10, new UpdateWater());
	public static final IWandRod rodObsidian = new WandRod("obsidian", 200, new ItemStack(TW_Items.itemWandRod, 1, 4), 10, new UpdateEarth());
	public static final IWandRod rodQuartz = new WandRod("quartz", 200, new ItemStack(TW_Items.itemWandRod, 1, 5), 10);
	public static final IWandRod rodBone = new WandRod("bone", 200, new ItemStack(TW_Items.itemWandRod, 1, 6), 10);
	public static final IWandRod rodSilverwood = new WandRod("silverwood", 400, new ItemStack(TW_Items.itemWandRod, 1, 7), 15);
	public static final IStaffCore coreGreatwood = new StaffCore("greatwood", 250, new ItemStack(TW_Items.itemStaffCore, 1, 0));
	public static final IStaffCore coreReed = new StaffCore("reed", 500, new ItemStack(TW_Items.itemStaffCore, 1, 1), new UpdateAir());
	public static final IStaffCore coreBlaze = new StaffCore("blaze", 500, new ItemStack(TW_Items.itemStaffCore, 1, 2), new UpdateFire());
	public static final IStaffCore coreIce = new StaffCore("ice", 500, new ItemStack(TW_Items.itemStaffCore, 1, 3), new UpdateWater());
	public static final IStaffCore coreObsidian = new StaffCore("obsidian", 500, new ItemStack(TW_Items.itemStaffCore, 1, 4), new UpdateEarth());
	public static final IStaffCore coreQuartz = new StaffCore("quartz", 500, new ItemStack(TW_Items.itemStaffCore, 1, 5));
	public static final IStaffCore coreBone = new StaffCore("bone", 500, new ItemStack(TW_Items.itemStaffCore, 1, 6));
	public static final IStaffCore coreSilverwood = new StaffCore("silverwood", 750, new ItemStack(TW_Items.itemStaffCore, 1, 7));
	public static final IStaffCore corePrimal = new StaffCore("primal", 750, new ItemStack(TW_Items.itemStaffCore, 1, 8), new UpdatePrimal());

	public static void registerWandParts() {
		for(IWandCap cap: CAPS)
			ThaumicWandsAPI.registerWandCap(cap.getTag(), cap);
		for(IWandRod rod: RODS)
			ThaumicWandsAPI.registerWandRod(rod.getTag(), rod);
		for(IStaffCore core: STAFF)
			ThaumicWandsAPI.registerStaffCore(core.getTag(), core);
	}

}
