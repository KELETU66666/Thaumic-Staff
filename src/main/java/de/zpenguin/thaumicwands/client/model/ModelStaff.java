package de.zpenguin.thaumicwands.client.model;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports

import de.zpenguin.thaumicwands.item.ItemStaff;
import de.zpenguin.thaumicwands.main.ThaumicWands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ModelStaff extends ModelBase {
    private final ModelRenderer head;
    private final ModelRenderer end;
    private final ModelRenderer rod;
    private final ModelRenderer focus;

    public ModelStaff() {
        textureWidth = 32;
        textureHeight = 32;

        rod = new ModelRenderer(this, 0, 8);
        rod.addBox(-1F, 0F, -1F, 2, 18, 2);
        rod.setRotationPoint(0, 2, 0);
        rod.setTextureSize(64, 64);

        end = new ModelRenderer(this, 0, 0);
        end.addBox(-1F, -1F, -1F, 2, 2, 2);
        end.setRotationPoint(0, 2, 0);
        end.setTextureSize(64, 32);

        head = new ModelRenderer(this, 0, 0);
        head.addBox(-1F, -1F, -1F, 2, 2, 2);
        head.setRotationPoint(0, 28, 0);
        head.setTextureSize(64, 32);

        focus = new ModelRenderer(this, 0, 0);
        focus.addBox(-1, -1, -1, 2, 2, 2);
        focus.setRotationPoint(0, 29, 0);
        focus.setTextureSize(32, 32);

    }

    public void render(ItemStack wandStack) {
        float scale = 1F / 32F;
        ItemStaff wand = (ItemStaff) wandStack.getItem();
        GL11.glPushMatrix();
        GlStateManager.scale(0.8, 1.4, 0.8);
        Minecraft.getMinecraft().renderEngine.bindTexture(wand.getCore(wandStack).getTexture());
        GlStateManager.translate(0.85, 0.125, 0.85);
        rod.render(scale);

        GlStateManager.scale(1 / 0.8, 1 / 1.4, 1 / 0.8);
        Minecraft.getMinecraft().renderEngine.bindTexture(wand.getCap(wandStack).getTexture());
        GL11.glScaled(1.275D, 1.0D, 1.275D);
        GlStateManager.enableLighting();
        head.render(scale);
        end.render(scale);

        if (wand.getFocus(wandStack) != null) {
            Color c = new Color(wand.getFocus(wandStack).getFocusColor(wand.getFocusStack(wandStack)));
            GL11.glColor3f(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
            GlStateManager.scale(1.1, 1.1, 1.1);
            GlStateManager.translate(0, -0.075, 0);
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ThaumicWands.modID, "textures/models/wand_focus.png"));
            focus.render(scale);
            GL11.glColor3f(1F, 1F, 1F);
        }
        GlStateManager.translate(0, -1, -1);
        GL11.glPopMatrix();
    }

}