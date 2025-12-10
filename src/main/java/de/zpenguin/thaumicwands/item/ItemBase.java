package de.zpenguin.thaumicwands.item;

import de.zpenguin.thaumicwands.main.ThaumicWands;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(name);
        this.setCreativeTab(ThaumicWands.TabTW);
        TW_Items.ITEMS.add(this);
    }

}
