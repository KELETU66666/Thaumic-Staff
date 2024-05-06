package de.zpenguin.thaumicwands.main;

import de.zpenguin.thaumicwands.item.TW_Items;
import de.zpenguin.thaumicwands.util.DummyRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.crafting.DustTriggerOre;
import thaumcraft.common.lib.crafting.DustTriggerSimple;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

@Mod(modid = ThaumicWands.modID, name = ThaumicWands.modName, version = ThaumicWands.version, dependencies = ThaumicWands.dependencies)
public class ThaumicWands {

	public static final String modID = "thaumicwands";
	public static final String modName = "Thaumic Wands";
	public static final String version = "1.5.0";
	public static final String dependencies = "required-after:thaumcraft;";
	
	public static final Logger logger = LogManager.getLogger("Thaumic Wands");

	private static final Field TRIGGER_RESEARCH_SIMPLE;
	private static final Field TRIGGER_RESEARCH_ORE;

	static {
		Field f = null;
		try {
			f = DustTriggerSimple.class.getDeclaredField("research");
			f.setAccessible(true);
		}
		catch (Exception ex) {
			FMLCommonHandler.instance().raiseException(ex, "Failed to access Thaumcraft's DustTriggerSimple#research", true);
		}

		TRIGGER_RESEARCH_SIMPLE = f;

		Field t = null;
		try {
			t = DustTriggerOre.class.getDeclaredField("research");
			t.setAccessible(true);
		}
		catch (Exception ex) {
			FMLCommonHandler.instance().raiseException(ex, "Failed to access Thaumcraft's DustTriggerSimple#research", true);
		}

		TRIGGER_RESEARCH_ORE = t;
	}

	public static String getDustTriggerSimpleResearch(DustTriggerSimple trigger) {
		try {
			return (String) TRIGGER_RESEARCH_SIMPLE.get(trigger);
		}
		catch (Exception ex) {
			FMLCommonHandler.instance().raiseException(ex, "Failed to invoke Thaumcraft's DustTriggerSimple#research", true);
			return null;
		}
	}

	public static String getDustTriggerOreResearch(DustTriggerOre trigger) {
		try {
			return (String) TRIGGER_RESEARCH_ORE.get(trigger);
		}
		catch (Exception ex) {
			FMLCommonHandler.instance().raiseException(ex, "Failed to invoke Thaumcraft's DustTriggerOre#research", true);
			return null;
		}
	}

	@Instance
	public static ThaumicWands instance;

	@SidedProxy(clientSide = "de.zpenguin.thaumicwands.client.ClientProxy", serverSide = "de.zpenguin.thaumicwands.main.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	public static void removeRecipeByName(@Nonnull ResourceLocation location) {
		ForgeRegistries.RECIPES.register(new DummyRecipe().setRegistryName(location));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
		for (int i = 0; i < IDustTrigger.triggers.size(); ++i) {
			IDustTrigger trigger = IDustTrigger.triggers.get(i);
			if (trigger instanceof DustTriggerSimple && (getDustTriggerSimpleResearch((DustTriggerSimple) trigger).equals("!gotdream") || getDustTriggerSimpleResearch((DustTriggerSimple) trigger).equals("FIRSTSTEPS@1"))) {
				IDustTrigger.triggers.remove(i);
				break;
			}
		}
		for (int i = 0; i < IDustTrigger.triggers.size(); ++i) {
			IDustTrigger trigger = IDustTrigger.triggers.get(i);
			if (trigger instanceof DustTriggerOre && (getDustTriggerOreResearch((DustTriggerOre) trigger).equals("!gotdream") || getDustTriggerOreResearch((DustTriggerOre) trigger).equals("FIRSTSTEPS@1"))) {
				IDustTrigger.triggers.remove(i);
				break;
			}
		}

		IDustTrigger.registerDustTrigger(new DustTriggerSimple("", Blocks.BOOKSHELF, new ItemStack(ItemsTC.thaumonomicon)));
		IDustTrigger.registerDustTrigger(new DustTriggerOre("", "bookshelf", new ItemStack(ItemsTC.thaumonomicon)));

		removeRecipeByName(new ResourceLocation("thaumcraft:salismundus"));

		GameRegistry.addSmelting(TW_Items.itemBalancedCluster,new ItemStack(ItemsTC.salisMundus,1), 1.0F);
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent e) {
		proxy.loadComplete(e);
	}

}
