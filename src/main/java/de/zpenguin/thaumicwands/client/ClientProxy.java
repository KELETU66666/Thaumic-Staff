package de.zpenguin.thaumicwands.client;

import de.zpenguin.thaumicwands.block.tile.TileNodeStabilizer;
import de.zpenguin.thaumicwands.client.model.BakedModelScepter;
import de.zpenguin.thaumicwands.client.model.BakedModelStaff;
import de.zpenguin.thaumicwands.client.model.BakedModelWand;
import de.zpenguin.thaumicwands.client.render.entity.EntityVisOrbRenderer;
import de.zpenguin.thaumicwands.client.render.entity.RenderAuraNode;
import de.zpenguin.thaumicwands.client.render.entity.RenderNodeMagnet;
import de.zpenguin.thaumicwands.client.render.item.ItemScepterRenderer;
import de.zpenguin.thaumicwands.client.render.item.ItemStaffRenderer;
import de.zpenguin.thaumicwands.client.render.item.ItemWandRenderer;
import de.zpenguin.thaumicwands.client.render.tile.TileArcaneWorktableRenderer;
import de.zpenguin.thaumicwands.client.render.tile.TileNodeStabilizerRenderer;
import de.zpenguin.thaumicwands.entity.EntityNodeMagnet;
import de.zpenguin.thaumicwands.entity.EntityVisOrb;
import de.zpenguin.thaumicwands.entity.node.EntityAuraNode;
import de.zpenguin.thaumicwands.item.TW_Items;
import de.zpenguin.thaumicwands.main.CommonProxy;
import de.zpenguin.thaumicwands.main.ThaumicWands;
import de.zpenguin.thaumicwands.tile.TileArcaneWorkbenchNew;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid=ThaumicWands.modID, value=Side.CLIENT)
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		TW_Items.itemWand.setTileEntityItemStackRenderer(new ItemWandRenderer());
		TW_Items.itemStaff.setTileEntityItemStackRenderer(new ItemStaffRenderer());
		TW_Items.itemScepter.setTileEntityItemStackRenderer(new ItemScepterRenderer());
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		ClientRegistry.bindTileEntitySpecialRenderer(TileArcaneWorkbenchNew.class, new TileArcaneWorktableRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileNodeStabilizer.class, new TileNodeStabilizerRenderer());

		ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(CommonProxy.nodeStabilizer), 0, TileNodeStabilizer.class);

		RenderingRegistry.registerEntityRenderingHandler(EntityVisOrb.class, new EntityVisOrbRenderer(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityAuraNode.class, new RenderAuraNode(Minecraft.getMinecraft().getRenderManager()));
		RenderingRegistry.registerEntityRenderingHandler(EntityNodeMagnet.class, new RenderNodeMagnet(Minecraft.getMinecraft().getRenderManager()));
	}

	@SubscribeEvent
	public static void bakeModel(ModelBakeEvent e) {
		e.getModelRegistry().putObject(new ModelResourceLocation("thaumicwands:item_wand_3d"), new BakedModelWand());
		e.getModelRegistry().putObject(new ModelResourceLocation("thaumicwands:item_staff_3d"), new BakedModelStaff());
		e.getModelRegistry().putObject(new ModelResourceLocation("thaumicwands:item_scepter_3d"), new BakedModelScepter());
	}

}
