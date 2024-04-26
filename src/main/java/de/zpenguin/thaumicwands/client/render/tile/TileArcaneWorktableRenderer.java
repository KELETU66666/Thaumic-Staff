package de.zpenguin.thaumicwands.client.render.tile;

import de.zpenguin.thaumicwands.client.model.ModelWand;
import de.zpenguin.thaumicwands.tile.TileArcaneWorkbenchNew;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;

public class TileArcaneWorktableRenderer extends TileEntitySpecialRenderer<TileArcaneWorkbenchNew> {

	static ModelWand model = new ModelWand();

	@Override
	public void render(TileArcaneWorkbenchNew te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		ItemStack wand = te.inventoryCraft.getStackInSlot(15);
		if(wand.isEmpty()) return;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y+1.6875, z - 0.2);
		//GlStateManager.scale(0.625, 0.625, 0.625);

		GlStateManager.rotate(90F, 1, 0, 0);
		GlStateManager.rotate(20F, 0, 0, 1);

		model.render(wand);

		GlStateManager.popMatrix();
	}

}
