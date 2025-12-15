package de.zpenguin.thaumicwands.client.render.entity;

import de.zpenguin.thaumicwands.api.item.wand.IStaff;
import de.zpenguin.thaumicwands.api.item.wand.IWand;
import de.zpenguin.thaumicwands.client.model.ModelWand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import static net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GUI;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemWandRenderer extends TileEntityItemStackRenderer {

    private static final ModelWand model = new ModelWand();
    public static ItemCameraTransforms.TransformType transform = GUI;

    @Override
    public void renderByItem(ItemStack item, float partialTicks) {
        if (item.isEmpty() || !(item.getItem() instanceof IWand)) {
            return;
        }

        final IWand wand = (IWand) item.getItem();
        //final ItemStack focusStack = wand.getFocusItem(item);
        final boolean staff = wand instanceof IStaff;

        EntityLivingBase wielder = null;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player != null && mc.player.getHeldItemMainhand() == item) {
            wielder = mc.player;
        } else if (mc.player != null && mc.player.getHeldItemOffhand() == item) {
            wielder = mc.player;
        }

        GlStateManager.pushMatrix();

        if (staff) {
            GlStateManager.translate(0.0, 0.5, 0.0);
        }

        GlStateManager.translate(0.5, 1.5, 0.5);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);

      /*  if (wielder != null && wielder instanceof EntityPlayer &&
            wielder.getActiveItemStack() == item) {

            float t = wielder.getItemInUseCount() + partialTicks;
            if (t > 3.0F) {
                t = 3.0F;
            }

            GlStateManager.translate(0.0, 1.0, 0.0);
            GlStateManager.rotate(33.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(60.0F * (t / 3.0F), -1.0F, 0.0F, 0.0F);

            if (wand.animation == ItemFocusBasic.WandFocusAnimation.WAVE ||
                (wand.getFocus(item) != null &&
                 wand.getFocus(item).getAnimation(focusStack) == ItemFocusBasic.WandFocusAnimation.WAVE)) {

                float wave = MathHelper.sin((wielder.getItemInUseCount() + partialTicks) / 10.0F) * 10.0F;
                GlStateManager.rotate(wave, 0.0F, 0.0F, 1.0F);
                wave = MathHelper.sin((wielder.getItemInUseCount() + partialTicks) / 15.0F) * 10.0F;
                GlStateManager.rotate(wave, 1.0F, 0.0F, 0.0F);

            } else if (wand.getFocus(item) != null &&
                       wand.getFocus(item).getAnimation(focusStack) == ItemFocusBasic.WandFocusAnimation.CHARGE) {

                float wave = MathHelper.sin((wielder.getItemInUseCount() + partialTicks) / 0.8F);
                GlStateManager.rotate(wave, 0.0F, 0.0F, 1.0F);
                wave = MathHelper.sin((wielder.getItemInUseCount() + partialTicks) / 0.7F);
                GlStateManager.rotate(wave, 1.0F, 0.0F, 0.0F);
            }

            GlStateManager.translate(0.0, -1.0, 0.0);
        }*/

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        model.render(item);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}