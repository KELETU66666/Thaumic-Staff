package de.zpenguin.thaumicwands.client.render.tile;

import de.zpenguin.thaumicwands.api.item.wand.IWand;
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
        if (wand.isEmpty()) return;

        GlStateManager.pushMatrix();

        if (wand.getItem() instanceof IWand) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x + 0.65f, (float) y + 1.0625f, (float) z + 0.25f);
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(20.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            model.render(wand);
            GlStateManager.popMatrix();
        }


        GlStateManager.popMatrix();
    }

}
