package de.zpenguin.thaumicwands.wand;

import de.zpenguin.thaumicwands.api.item.wand.IStaffCore;
import de.zpenguin.thaumicwands.api.item.wand.IWandUpdate;
import de.zpenguin.thaumicwands.main.ThaumicWands;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class StaffCore implements IStaffCore {

    int capacity;

    String tag;
    String research;
    ItemStack item;
    IWandUpdate update;


    public StaffCore(String tag, int capacity, ItemStack item) {
        this(tag, capacity, item, null, "ROD_" + tag.toUpperCase());
    }

    public StaffCore(String tag, int capacity, ItemStack item, IWandUpdate update) {
        this(tag, capacity, item, update, "ROD_" + tag.toUpperCase());
    }

    public StaffCore(String tag, int capacity, ItemStack item, String research) {
        this(tag, capacity, item, null, research);
    }

    public StaffCore(String tag, int capacity, ItemStack item, IWandUpdate update, String research) {
        this.tag = tag;
        this.capacity = capacity;
        this.item = item;
        this.update = update;
        this.research = research;

        TW_Wands.STAFF.add(this);
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(ThaumicWands.modID, "textures/models/wand_rod_" + tag + ".png");
    }

    @Override
    public ItemStack getItemStack() {
        return item;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String getRequiredResearch() {
        return research;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public IWandUpdate getUpdate() {
        return update;
    }

}
