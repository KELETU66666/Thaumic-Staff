package de.zpenguin.thaumicwands.client.model;

import de.zpenguin.thaumicwands.item.ItemScepter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ModelScepter extends ModelBase {

    final ModelRenderer rod;
    final ModelRenderer capTop;
    final ModelRenderer capMiddle;
    final ModelRenderer capBottom;

    public ModelScepter() {
        textureWidth = 32;
        textureHeight = 32;

        rod = new ModelRenderer(this, 0, 8);
        rod.addBox(-1F, 0F, -1F, 2, 18, 2);
        rod.setRotationPoint(0, 2, 0);
        rod.setTextureSize(64, 64);

        capTop = new ModelRenderer(this, 0, 0);
        capTop.addBox(-1F, -1F, -1F, 2, 2, 2);
        capTop.setRotationPoint(0, 2, 0);
        capTop.setTextureSize(64, 32);

        capMiddle = new ModelRenderer(this, 0, 0);
        capMiddle.addBox(-1F, -1F, -1F, 2, 1, 2);
        capMiddle.setRotationPoint(0, 18, 0);
        capMiddle.setTextureSize(64, 18);

        capBottom = new ModelRenderer(this, 0, 0);
        capBottom.addBox(-1, -1, -1, 2, 2, 2);
        capBottom.setRotationPoint(0, 20, 0);
        capBottom.setTextureSize(32, 32);

    }

    public void render(ItemStack wandStack) {
        float scale = 1F / 32F;
        ItemScepter wand = (ItemScepter) wandStack.getItem();
        Minecraft.getMinecraft().renderEngine.bindTexture(wand.getRod(wandStack).getTexture());
        GL11.glPushMatrix();
        GlStateManager.translate(0.66375, 0.125, 0.66375);
        rod.render(scale);
        Minecraft.getMinecraft().renderEngine.bindTexture(wand.getCap(wandStack).getTexture());
        GL11.glScaled(1.275D, 1.0D, 1.275D);
        GlStateManager.enableLighting();
        capTop.render(scale);
        capBottom.render(scale);
        GlStateManager.scale(1.2, 1.0, 1.2);
        capMiddle.render(scale);

        GL11.glPopMatrix();
    }
}
