package de.zpenguin.thaumicwands.main;

import de.zpenguin.thaumicwands.compat.TW_Compat;
import de.zpenguin.thaumicwands.item.TW_Items;
import de.zpenguin.thaumicwands.tile.TW_Tiles;
import de.zpenguin.thaumicwands.wand.TW_Wands;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.AspectList;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        TW_Tiles.registerTiles();
        TW_Compat.preInit(e);
    }

    public void init(FMLInitializationEvent e) {
        TW_Compat.init();
        TW_Research.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(ThaumicWands.instance, new TW_GuiHandler());
        TW_Wands.registerWandParts();

    }

    public void postInit(FMLPostInitializationEvent e) {
        TW_Research.postInit();
        ThaumcraftApi.registerObjectTag(new ItemStack(TW_Items.itemWand), new AspectList());
        ThaumcraftApi.registerObjectTag(new ItemStack(TW_Items.itemScepter), new AspectList());
        ThaumcraftApi.registerObjectTag(new ItemStack(TW_Items.itemStaff), new AspectList());
    }

    public void loadComplete(FMLLoadCompleteEvent e) {

    }

}
