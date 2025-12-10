package de.zpenguin.thaumicwands.client.render.item;

import de.zpenguin.thaumicwands.client.model.ModelWand;
import de.zpenguin.thaumicwands.item.ItemWand;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import static net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.*;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;


public class ItemWandRenderer extends TileEntityItemStackRenderer {

    public static TransformType transform = GUI;

    private final ModelWand modelWand = new ModelWand();

    @Override
    public void renderByItem(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemWand))
            return;

        GlStateManager.pushMatrix();

        if (transform == GUI) {
            GlStateManager.scale(1.75, 1.75, 1.75);
            GlStateManager.translate(1.25, 0.6, 0);
            GlStateManager.rotate(180, 1, 0, 0);
            GlStateManager.rotate(135, 0, 0, 1);
            GlStateManager.rotate(60, 0, 1, 0);
            modelWand.render(stack);
        }

        if (transform == GROUND) {
            GlStateManager.scale(0.8, 1, 0.8);
            GlStateManager.translate(0, 0.5, 0);
        }

        if (transform == FIRST_PERSON_RIGHT_HAND || transform == FIRST_PERSON_LEFT_HAND) {
            GlStateManager.scale(0.8, 1, 0.8);
        }

        if (transform == THIRD_PERSON_LEFT_HAND || transform == THIRD_PERSON_RIGHT_HAND) {
            GlStateManager.translate(-0.175, 0, -0.125);

        }

        modelWand.render(stack);
        GlStateManager.popMatrix();
    }

}
