package de.zpenguin.thaumicwands.entity;

import de.zpenguin.thaumicwands.entity.node.EntityAuraNode;
import de.zpenguin.thaumicwands.main.ThaumicWands;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class TW_Entities {

	public static void registerEntities(Register<EntityEntry> r) {
		EntityRegistry.registerModEntity(new ResourceLocation(ThaumicWands.modID, "VisOrb"), EntityVisOrb.class, "visOrb", 0, ThaumicWands.instance, 120, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ThaumicWands.modID, "AuraNode"), EntityAuraNode.class, "AuraNode", 1, ThaumicWands.instance, 160, 20, true);
		EntityRegistry.registerModEntity(new ResourceLocation(ThaumicWands.modID, "NodeMagnet"), EntityNodeMagnet.class, "NodeMagnet", 2, ThaumicWands.instance, 64, 3, true);
	}

}
