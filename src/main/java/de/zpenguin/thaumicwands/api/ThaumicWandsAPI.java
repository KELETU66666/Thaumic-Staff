package de.zpenguin.thaumicwands.api;

import java.util.ArrayList;
import java.util.HashMap;

import de.zpenguin.thaumicwands.api.item.wand.IStaffCore;
import de.zpenguin.thaumicwands.api.item.wand.IWandCap;
import de.zpenguin.thaumicwands.api.item.wand.IWandRod;
import de.zpenguin.thaumicwands.api.item.wand.IWandTrigger;

public class ThaumicWandsAPI {

	public static final HashMap<String, IWandRod> wandRods = new HashMap<>();
	public static final HashMap<String, IWandCap> wandCaps = new HashMap<>();
	public static final HashMap<String, IStaffCore> staffCore = new HashMap<>();
	public static final ArrayList<IWandTrigger> wandTriggers = new ArrayList<>();

	public static void registerWandRod(String name, IWandRod rod) {
		wandRods.put(name, rod);
	}

	public static void registerWandCap(String name, IWandCap cap) {
		wandCaps.put(name, cap);
	}

	public static void registerStaffCore(String name, IStaffCore core) {
		staffCore.put(name, core);
	}

	public static IWandRod getWandRod(String s) {
		return wandRods.get(s);
	}

	public static IWandCap getWandCap(String s) {
		return wandCaps.get(s);
	}

	public static IStaffCore getStaffCore(String s) {
		return staffCore.get(s);
	}

	public static void registerWandTrigger(IWandTrigger trigger) {
		wandTriggers.add(trigger);
	}

}
