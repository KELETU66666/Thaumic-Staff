// Decompiled with: CFR 0.152
// Class Version: 6
package de.zpenguin.thaumicwands.client.render.entity;

import de.zpenguin.thaumicwands.entity.node.EntityAuraNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.items.tools.ItemThaumometer;
import thaumcraft.common.lib.utils.EntityUtils;

@SideOnly(value=Side.CLIENT)
public class RenderAuraNode
extends Render<EntityAuraNode> {
    int size1 = 4;
    int size2 = 0;
    public static final ResourceLocation texture = new ResourceLocation("thaumicwands", "textures/misc/nodes.png");

    public RenderAuraNode(RenderManager rm) {
        super(rm);
        this.shadowSize = 0.0f;
    }

    public void doRender(EntityAuraNode entity, double x, double y, double z, float fq, float pticks) {
        if (entity.isDead) {
            return;
        }
        double vr = 8000.0;
        long t = System.currentTimeMillis();
        boolean canSee = EntityUtils.hasRevealer(Minecraft.getMinecraft().getRenderViewEntity());
        if (!canSee) {
            canSee = Minecraft.getMinecraft().player.getHeldItemMainhand() != null && Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemThaumometer && EntityUtils.isVisibleTo(0.8f, Minecraft.getMinecraft().getRenderViewEntity(), entity, 16.0f);
            vr = 300.0;
        }
        if (!canSee) {
            return;
        }
        double d = entity.getDistance(Minecraft.getMinecraft().getRenderViewEntity());
        if (d > vr) {
            return;
        }
        float alpha = 1.0f - (float)Math.min(1.0, d / (vr * (double)0.9f));
        int color = 0x888888;
        int blend = 1;
        int type = 1;
        float size = 0.15f + (float)entity.getNodeSize() / ((float)100 * 1.5f);
        if (entity.getAspect() != null) {
            color = entity.getAspect().getColor();
            blend = entity.getAspect().getBlend();
            type = 1 + entity.getNodeType();
        }
        GlStateManager.pushMatrix();
        this.bindTexture(texture);
        GlStateManager.disableDepth();
        UtilsFX.renderFacingQuad(entity.posX, entity.posY, entity.posZ, 32, 32, entity.ticksExisted % 32, size, color, 0.75f * alpha, blend, pticks);
        float s = 1.0f - MathHelper.sin(((float)entity.ticksExisted + pticks) / 8.0f) / 5.0f;
        UtilsFX.renderFacingQuad(entity.posX, entity.posY, entity.posZ, 32, 32, 800 + entity.ticksExisted % 16, s * size * 0.7f, color, 0.9f * alpha, blend, pticks);
        UtilsFX.renderFacingQuad(entity.posX, entity.posY, entity.posZ, 32, 32, 32 * type + entity.ticksExisted % 32, size / 3.0f, 0xFFFFFF, alpha, type == 2 ? 771 : 1, pticks);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
        if (d < 30.0) {
            float sc = 1.0f - (float)Math.min(1.0, d / 25.0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GlStateManager.scale(0.025 * (double)sc, 0.025 * (double)sc, 0.025 * (double)sc);
            UtilsFX.rotateToPlayer();
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            UtilsFX.drawTag(-8, -32, entity.getAspect(), entity.getNodeSize(), 0, 0.0);
            GlStateManager.scale(0.5, 0.5, 0.5);
            String text = I18n.format("nodetype." + entity.getNodeType());
            int sw = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
            Minecraft.getMinecraft().fontRenderer.drawString(text, (float)(-sw) / 2.0f, -72.0f, 0xFFFFFF, false);
            GlStateManager.popMatrix();
        }
    }

    protected ResourceLocation getEntityTexture(EntityAuraNode entity) {
        return texture;
    }
}
